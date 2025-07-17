package com.github.jakicdong.techub.service.sitemap.service.impl;

import com.github.jakicdong.techub.service.sitemap.constants.SitemapConstants;
import com.github.jakicdong.techub.service.sitemap.model.SiteCntVo;
import com.github.jakicdong.techub.service.sitemap.service.SitemapService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import com.github.jakicdong.techub.core.cache.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SitemapServiceImpl implements SitemapService {


    /**
     * 保存站点数据模型
     * <p>
     * 站点统计hash：
     * - visit_info:
     * ---- pv: 站点的总pv
     * ---- uv: 站点的总uv
     * ---- pv_path: 站点某个资源的总访问pv
     * ---- uv_path: 站点某个资源的总访问uv
     * - visit_info_ip:
     * ---- pv: 用户访问的站点总次数
     * ---- path_pv: 用户访问的路径总次数
     * - visit_info_20230822每日记录, 一天一条记录
     * ---- pv: 12  # field = 月日_pv, pv的计数
     * ---- uv: 5   # field = 月日_uv, uv的计数
     * ---- pv_path: 2 # 资源的当前访问计数
     * ---- uv_path: # 资源的当天访问uv
     * ---- pv_ip: # 用户当天的访问次数
     * ---- pv_path_ip: # 用户对资源的当天访问次数
     *
     * @param visitIp 访问者ip
     * @param path    访问的资源路径
     */
    @Override
    public void saveVisitInfo(String visitIp, String path) {
        String globalKey = SitemapConstants.SITE_VISIT_KEY;
        String day = SitemapConstants.day(LocalDate.now());
        String todayKey = globalKey + "_" + day;
        // 用户的全局访问计数+1
        Long globalUserVisitCnt = RedisClient.hIncr(globalKey + "_" + visitIp, "pv", 1);
        // 用户的当日访问计数+1
        Long todayUserVisitCnt = RedisClient.hIncr(todayKey, "pv_" + visitIp, 1);

        RedisClient.PipelineAction pipelineAction = RedisClient.pipelineAction();
        if (globalUserVisitCnt == 1) {
            // 站点新用户
            // 今日的uv + 1
            pipelineAction.add(todayKey, "uv"
                    , (connection, key, field) -> {
                        connection.hIncrBy(key, field, 1);
                    });
            pipelineAction.add(todayKey, "uv_" + path
                    , (connection, key, field) -> connection.hIncrBy(key, field, 1));

            // 全局站点的uv
            pipelineAction.add(globalKey, "uv", (connection, key, field) -> connection.hIncrBy(key, field, 1));
            pipelineAction.add(globalKey, "uv_" + path, (connection, key, field) -> connection.hIncrBy(key, field, 1));
        } else if (todayUserVisitCnt == 1) {
            // 判断是今天的首次访问，更新今天的uv+1
            pipelineAction.add(todayKey, "uv", (connection, key, field) -> connection.hIncrBy(key, field, 1));
            if (RedisClient.hIncr(todayKey, "pv_" + path + "_" + visitIp, 1) == 1) {
                // 判断是否为今天首次访问这个资源，若是，则uv+1
                pipelineAction.add(todayKey, "uv_" + path, (connection, key, field) -> connection.hIncrBy(key, field, 1));
            }

            // 判断是否是用户的首次访问这个path，若是，则全局的path uv计数需要+1
            if (RedisClient.hIncr(globalKey + "_" + visitIp, "pv_" + path, 1) == 1) {
                pipelineAction.add(globalKey, "uv_" + path, (connection, key, field) -> connection.hIncrBy(key, field, 1));
            }
        }
        // 更新pv 以及 用户的path访问信息
        // 今天的相关信息 pv
        pipelineAction.add(todayKey, "pv", (connection, key, field) -> connection.hIncrBy(key, field, 1));
        pipelineAction.add(todayKey, "pv_" + path, (connection, key, field) -> connection.hIncrBy(key, field, 1));
        if (todayUserVisitCnt > 1) {
            // 非当天首次访问，则pv+1; 因为首次访问时，在前面更新uv时，已经计数+1了
            pipelineAction.add(todayKey, "pv_" + path + "_" + visitIp, (connection, key, field) -> connection.hIncrBy(key, field, 1));
        }


        // 全局的 PV
        pipelineAction.add(globalKey, "pv", (connection, key, field) -> connection.hIncrBy(key, field, 1));
        pipelineAction.add(globalKey, "pv" + "_" + path, (connection, key, field) -> connection.hIncrBy(key, field, 1));

        // 保存访问信息
        pipelineAction.execute();
        if (log.isDebugEnabled()) {
            log.info("用户访问信息更新完成! 当前用户总访问: {}，今日访问: {}", globalUserVisitCnt, todayUserVisitCnt);
        }
    }

    /**
     * 查询站点某一天or总的访问信息
     *
     * @param date 日期，为空时，表示查询所有的站点信息
     * @param path 访问路径，为空时表示查站点信息
     * @return
     */
    @Override
    public SiteCntVo querySiteVisitInfo(LocalDate date, String path) {
        String globalKey = SitemapConstants.SITE_VISIT_KEY;
        String day = null, todayKey = globalKey;
        if (date != null) {
            day = SitemapConstants.day(date);
            todayKey = globalKey + "_" + day;
        }

        String pvField = "pv", uvField = "uv";
        if (path != null) {
            // 表示查询对应路径的访问信息
            pvField += "_" + path;
            uvField += "_" + path;
        }

        Map<String, Integer> map = RedisClient.hMGet(todayKey, Arrays.asList(pvField, uvField), Integer.class);
        SiteCntVo siteInfo = new SiteCntVo();
        siteInfo.setDay(day);
        siteInfo.setPv(map.getOrDefault(pvField, 0));
        siteInfo.setUv(map.getOrDefault(uvField, 0));
        return siteInfo;
    }

}

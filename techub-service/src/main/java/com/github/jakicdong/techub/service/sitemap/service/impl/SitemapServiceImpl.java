package com.github.jakicdong.techub.service.sitemap.service.impl;

import com.github.jakicdong.techub.service.sitemap.constants.SitemapConstants;
import com.github.jakicdong.techub.service.sitemap.model.SiteCntVo;
import com.github.jakicdong.techub.service.sitemap.service.SitemapService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import com.github.jakicdong.techub.core.cache.RedisClient;
import org.springframework.stereotype.Service;

@Service
public class SitemapServiceImpl implements SitemapService {




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

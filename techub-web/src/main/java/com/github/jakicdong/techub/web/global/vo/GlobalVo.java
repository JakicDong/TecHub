package com.github.jakicdong.techub.web.global.vo;
/*
* @author JakicDong
* @description 全局Vo
* @time 2025/7/4 11:26
*/

import com.github.jakicdong.techub.api.model.vo.seo.SeoTagVo;
import com.github.jakicdong.techub.api.model.vo.user.dto.BaseUserInfoDTO;
import com.github.jakicdong.techub.service.sitemap.model.SiteCntVo;
import com.github.jakicdong.techub.web.config.GlobalViewConfig;
import lombok.Data;

import java.util.List;

@Data
public class GlobalVo {
    /**
     * 网站相关配置
     */
    private GlobalViewConfig siteInfo;
    /**
     * 站点统计信息
     */
    private SiteCntVo siteStatisticInfo;

    /**
     * 今日的站点统计信息
     */
    private SiteCntVo todaySiteStatisticInfo;

    /**
     * 环境
     */
    private String env;

    /**
     * 是否已登录
     */
    private Boolean isLogin;

    /**
     * 登录用户信息
     */
    private BaseUserInfoDTO user;

    /**
     * 消息通知数量
     */
    private Integer msgNum;

    /**
     * 在线用户人数
     */
    private Integer onlineCnt;

    private String currentDomain;

    private List<SeoTagVo> ogp;
    private String jsonLd;

}

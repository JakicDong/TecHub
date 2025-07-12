package com.github.jakicdong.techub.service.sitemap.service;

import com.github.jakicdong.techub.service.sitemap.model.SiteCntVo;

import java.time.LocalDate;

/*
* @author JakicDong
* @description 站点地图服务
* @time 2025/7/4 18:36
*/
public interface SitemapService {


    /**
     * 保存用户访问信息
     * @param visitIp 访问者ip
     * @param path    访问的资源路径
     */
    void saveVisitInfo(String visitIp, String path);



    /**
     * 查询站点某一天or总的访问信息
     *
     * @param date 日期，为空时，表示查询所有的站点信息
     * @param path 访问路径，为空时表示查站点信息
     * @return
     */
    SiteCntVo querySiteVisitInfo(LocalDate date, String path);
}

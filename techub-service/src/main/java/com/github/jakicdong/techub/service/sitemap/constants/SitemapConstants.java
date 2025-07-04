package com.github.jakicdong.techub.service.sitemap.constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
* @author JakicDong
* @description 站点地图相关
* @time 2025/7/4 18:43
*/
public class SitemapConstants {
    public static final String SITE_VISIT_KEY = "visit_info";

    public static String day(LocalDate day) {
        return DateTimeFormatter.ofPattern("yyyyMMdd").format(day);
    }
}

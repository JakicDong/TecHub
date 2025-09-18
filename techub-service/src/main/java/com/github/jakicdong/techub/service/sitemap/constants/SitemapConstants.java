package com.github.jakicdong.techub.service.sitemap.constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/*
* @author JakicDong
* @description 站点地图常量类
* @time 2025/9/18 16:10
*/
public class SitemapConstants {
    public static final String SITE_VISIT_KEY = "visit_info";

    public static String day(LocalDate day) {
        return DateTimeFormatter.ofPattern("yyyyMMdd").format(day);
    }
}

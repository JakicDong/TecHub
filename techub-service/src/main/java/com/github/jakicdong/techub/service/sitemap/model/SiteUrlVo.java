package com.github.jakicdong.techub.service.sitemap.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* @author JakicDong
* @description 站点地图url实体类
* @time 2025/9/18 16:09
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "url")
public class SiteUrlVo {

    @JacksonXmlProperty(localName = "loc")
    private String loc;

    @JacksonXmlProperty(localName = "lastmod")
    private String lastMod;

}

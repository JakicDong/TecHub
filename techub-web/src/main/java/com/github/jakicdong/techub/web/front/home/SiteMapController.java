package com.github.jakicdong.techub.web.front.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.github.jakicdong.techub.service.sitemap.model.SiteMapVo;
import com.github.jakicdong.techub.service.sitemap.service.SitemapService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/*
* @author JakicDong
* @description 生成 sitemap.xml
* @time 2025/9/18 16:07
*/
@RestController
public class SiteMapController {
    private XmlMapper xmlMapper = new XmlMapper();
    @Resource
    private SitemapService sitemapService;

    @RequestMapping(path = "/sitemap",
            produces = "application/xml;charset=utf-8")
    public SiteMapVo sitemap() {
        return sitemapService.getSiteMap();
    }

    @RequestMapping(path = "/sitemap.xml",
            produces = "text/xml")
    public byte[] sitemapXml() throws JsonProcessingException {
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        SiteMapVo vo = sitemapService.getSiteMap();
        String ans = xmlMapper.writeValueAsString(vo);
        ans = ans.replaceAll(" xmlns=\"\"", "");

        return ans.getBytes(Charset.defaultCharset());
    }

    @GetMapping(path = "/sitemap/refresh")
    public Boolean refresh() {
        sitemapService.refreshSitemap();
        return true;
    }
}

package com.github.jakicdong.techub.web.global;


import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.vo.seo.Seo;
import com.github.jakicdong.techub.api.model.vo.seo.SeoTagVo;
import com.github.jakicdong.techub.web.config.GlobalViewConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* @author JakicDong
* @description
 * seo注入服务，下面加个页面使用
 * - 首页
 * - 文章详情页
 * - 用户主页
 * - 专栏内容详情页
* @time 2025/7/4 18:32
*/
@Service
public class SeoInjectService {
    private static final String KEYWORDS = "TecHub,开源社区,java,springboot,IT,程序员,开发者,mysql,redis,Java基础,多线程,JVM,虚拟机,数据库,MySQL,Spring,Redis,MyBatis,系统设计,分布式,RPC,高可用,高并发,JakicDong";
    private static final String DES = "TecHub,一个基于 Spring Boot、MyBatis-Plus、MySQL、Redis、ElasticSearch、MongoDB、Docker、RabbitMQ 等技术栈实现的社区系统，采用主流的互联网技术架构、全新的UI设计、支持一键源码部署，拥有完整的文章&教程发布/搜索/评论/统计流程等，代码完全开源，没有任何二次封装，是一个非常适合二次开发/实战的现代化社区项目。";

    @Resource
    private GlobalViewConfig globalViewConfig;
    private Seo initBasicSeoTag() {

        List<SeoTagVo> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String url = globalViewConfig.getHost() + request.getRequestURI();

        list.add(new SeoTagVo("og:url", url));
        map.put("url", url);

        return Seo.builder().jsonLd(map).ogp(list).build();
    }

    public Seo defaultSeo() {
        Seo seo = initBasicSeoTag();
        List<SeoTagVo> list = seo.getOgp();
        list.add(new SeoTagVo("og:title", "TecHub"));
        list.add(new SeoTagVo("og:description", DES));
        list.add(new SeoTagVo("og:type", "article"));
        list.add(new SeoTagVo("og:locale", "zh-CN"));

        list.add(new SeoTagVo("article:tag", "后端,前端,Java,Spring,计算机"));
        list.add(new SeoTagVo("article:section", "开源社区"));
        list.add(new SeoTagVo("article:author", "TecHub"));

        list.add(new SeoTagVo("author", "TecHub"));
        list.add(new SeoTagVo("title", "TecHub"));
        list.add(new SeoTagVo("description", DES));
        list.add(new SeoTagVo("keywords", KEYWORDS));

        Map<String, Object> jsonLd = seo.getJsonLd();
        jsonLd.put("@context", "https://schema.org");
        jsonLd.put("@type", "Article");
        jsonLd.put("headline", "TecHub");
        jsonLd.put("description", DES);

        if (ReqInfoContext.getReqInfo() != null) {
            ReqInfoContext.getReqInfo().setSeo(seo);
        }
        return seo;
    }

}

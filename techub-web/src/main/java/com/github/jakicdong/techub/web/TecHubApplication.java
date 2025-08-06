package com.github.jakicdong.techub.web;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jakicdong.techub.core.util.SocketUtil;
import com.github.jakicdong.techub.web.config.GlobalViewConfig;
import com.github.jakicdong.techub.web.global.ForumExceptionHandler;
import com.github.jakicdong.techub.web.hook.interceptor.GlobalViewInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/*
* @author JakicDong
* @description TecHubApplication入口类
* @time 2025/7/1 19:25
*/
@Slf4j
@EnableAsync
//@EnableScheduling
@EnableCaching
@ServletComponentScan
@SpringBootApplication
public class TecHubApplication implements WebMvcConfigurer, ApplicationRunner {
    @Value("${server.port:8080}")
    private Integer webPort;

    //全局视图拦截器
    @Resource
    private GlobalViewInterceptor globalViewInterceptor;

    //注册全局拦截器,拦截所有的请求
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalViewInterceptor).addPathPatterns("/**");
    }

    //添加全局异常处理器
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, new ForumExceptionHandler());
    }


    public static void main(String[] args) {
        SpringApplication.run(TecHubApplication.class, args);
    }

    /**
     * 兼容本地启动时8080端口被占用的场景 (只有dev启动方式才做这个逻辑)
     */
    @Bean
    @ConditionalOnExpression(value = "#{'dev'.equals(environment.getProperty('env.name'))}")
    public TomcatConnectorCustomizer customServerPortTomcatConnectorCustomizer() {
        // 开发环境时，首先判断8080d端口是否可用；若可用则直接使用，否则选择一个可用的端口号启动
        int port = SocketUtil.findAvailableTcpPort(8000, 10000, webPort);
        if (port != webPort) {
            log.info("----------------------------------------------------------------");
            log.info("            默认端口号{}被占用，随机启用新端口号: {}      ", webPort, port);
            log.info("----------------------------------------------------------------");
            webPort = port;
        }
        return connector -> connector.setPort(port);
    }

    /*
     * @description 应用启动之后执行
     */
    @Override
    public void run(ApplicationArguments args) {
        // 设置类型转换, 主要用于mybatis读取varchar/json类型数据据，并写入到json格式的实体Entity中
        JacksonTypeHandler.setObjectMapper(new ObjectMapper());
        //动态设置主机地址
        GlobalViewConfig config = SpringUtil.getBean(GlobalViewConfig.class);
        if (webPort != null) {
            config.setHost("http://127.0.0.1:" + webPort);
        }
        log.info("----------------------------------------------------------------");
        log.info("             启动成功，点击进入首页: {}               ", config.getHost());
        log.info("----------------------------------------------------------------");
    }

    /**
     * 解决swagger-ui访问 /doc.html 404问题
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

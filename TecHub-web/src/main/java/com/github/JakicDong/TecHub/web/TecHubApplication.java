package com.github.JakicDong.TecHub.web;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.JakicDong.TecHub.web.config.GlobalViewConfig;
import com.github.JakicDong.TecHub.core.util.SocketUtil;
import com.github.JakicDong.TecHub.web.hook.interceptor.GlobalViewInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author JakicDong
 * @time 2025.6.25
 * @description 启动类
 * @version 1.0.0
 */


@Slf4j // 日志注解
@ServletComponentScan // 扫描servlet组件
@SpringBootApplication // 启动类
//@SpringBootApplication(scanBasePackages = {"com.github.JakicDong.TecHub.web"
//        , "com.github.JakicDong.TecHub.service"
//        , "com.github.JakicDong.TecHub.core"})

public class TecHubApplication implements WebMvcConfigurer , ApplicationRunner {

    @Value("${server.port:8080}")
    private Integer webPort;

    //注入全局的配置信息
    @Resource
    private GlobalViewInterceptor globalViewInterceptor;

//    @Resource
//    private AsyncHandlerInterceptor onlineUserStatisticInterceptor;


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(globalViewInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(onlineUserStatisticInterceptor).addPathPatterns("/**").excludePathPatterns("/test/**").excludePathPatterns("/subscribe");
//    }


    public static void main(String[] args) {
        SpringApplication.run(TecHubApplication.class, args);
    }

    // 跨域设置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //项目中的所有接口都支持跨域
        registry.addMapping("/**")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE");
    }

    /**
     * 兼容本地启动时8080端口被占用的场景; 只有dev启动方式才做这个逻辑
     *
     * @return
     */
    @Bean
    @ConditionalOnExpression(value = "#{'dev'.equals(environment.getProperty('env.name'))}")
    public TomcatConnectorCustomizer customServerPortTomcatConnectorCustomizer() {
        // 开发环境时，首先判断8080d端口是否可用；若可用则直接使用，否则选择一个可用的端口号启动
        int port = SocketUtil.findAvailableTcpPort(8000, 10000, webPort);
        if (port != webPort) {
            log.info("默认端口号{}被占用，随机启用新端口号: {}", webPort, port);
            webPort = port;
        }
        return connector -> connector.setPort(port);
    }

    @Override
    public void run(ApplicationArguments args) {
        // 设置类型转换, 主要用于mybatis读取varchar/json类型数据据，并写入到json格式的实体Entity中
        JacksonTypeHandler.setObjectMapper(new ObjectMapper());
        // 应用启动之后执行
        GlobalViewConfig config = SpringUtil.getBean(GlobalViewConfig.class);
        if (webPort != null) {
            config.setHost("http://127.0.0.1:" + webPort);
        }
        log.info("启动成功，点击进入首页: {}", config.getHost());
    }
}

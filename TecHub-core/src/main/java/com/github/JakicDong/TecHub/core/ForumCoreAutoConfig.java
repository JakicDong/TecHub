package com.github.JakicDong.TecHub.core;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author JakicDong
 * @time 2025.6.28
 * @description core模块的自动配置类
 * @version 1.0.0
 */
@Configuration
//@EnableConfigurationProperties(ProxyProperties.class)
@ComponentScan(basePackages = "com.github.JakicDong.TecHub.core")
public class ForumCoreAutoConfig {
}

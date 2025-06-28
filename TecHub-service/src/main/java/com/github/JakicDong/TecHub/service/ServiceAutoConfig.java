package com.github.JakicDong.TecHub.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @author JakicDong
 * @time 2025.6.28
 * @description service模块的自动配置类
 * @version 1.0.0
 */
@Configuration
@ComponentScan("com.github.JakicDong.TecHub.service")
//@MapperScan(basePackages = {
//        "com.github.JakicDong.TecHub.service.article.repository.mapper",
//        "com.github.JakicDong.TecHub.service.user.repository.mapper",
//        "com.github.JakicDong.TecHub.service.comment.repository.mapper",
//        "com.github.JakicDong.TecHub.service.config.repository.mapper",
//        "com.github.JakicDong.TecHub.service.statistics.repository.mapper",
//        "com.github.JakicDong.TecHub.service.notify.repository.mapper",})
public class ServiceAutoConfig {

}

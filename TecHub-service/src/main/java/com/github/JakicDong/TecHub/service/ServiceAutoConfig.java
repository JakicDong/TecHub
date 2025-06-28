package com.github.JakicDong.TecHub.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author XuYifei
 * @date 2024-07-12
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

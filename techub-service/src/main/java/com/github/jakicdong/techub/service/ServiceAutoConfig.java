package com.github.jakicdong.techub.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author YiHui
 * @date 2022/7/6
 */
@Configuration
@ComponentScan("com.github.jakicdong.techub.service")
@MapperScan(basePackages = {
        "com.github.jakicdong.techub.service.article.repository.mapper",
        "com.github.jakicdong.techub.service.user.repository.mapper",
        "com.github.jakicdong.techub.service.comment.repository.mapper",
        "com.github.jakicdong.techub.service.config.repository.mapper",
        "com.github.jakicdong.techub.service.statistics.repository.mapper",
        "com.github.jakicdong.techub.service.notify.repository.mapper",
        "com.github.jakicdong.techub.service.shortlink.repository.mapper",
})
public class ServiceAutoConfig {


}

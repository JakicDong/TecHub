package com.github.jakicdong.techub.service.chatai.service.impl.doubao;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
* @author JakicDong
* @description  豆包配置
* @time 2025/9/20 19:42
*/
@Data
@Configuration
@ConfigurationProperties(prefix = "doubao")
public class DoubaoConfig{

    @Value("${doubao.api-key}")
    private String apiKey;
    @Value("${doubao.api-host}")
    private String apiHost;
    @Value("${doubao.end-point}")
    private String endPoint;
}
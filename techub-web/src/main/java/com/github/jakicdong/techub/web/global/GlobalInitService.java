package com.github.jakicdong.techub.web.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
* @author JakicDong
* @description 全局初始化服务
* @time 2025/7/1 20:19
*/
@Slf4j
@Service
public class GlobalInitService {
    @Value("${env.name}")
    private String env;

    //todo 实现全局初始化服务

}

package com.github.JakicDong.TecHub.web.hook.interceptor;


import com.github.JakicDong.TecHub.web.global.GlobalInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 注入全局的配置信息：
 * 站点信息，基本信息，在这里注入
 *
 * @author JakicDOng
 * @date 2025-6-27
 */
@Slf4j
@Component
@Order(-1)
public class GlobalViewInterceptor {
    @Autowired
    private GlobalInitService globalInitService;



}


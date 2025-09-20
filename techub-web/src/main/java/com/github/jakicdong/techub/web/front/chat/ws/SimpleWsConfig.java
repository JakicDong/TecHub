package com.github.jakicdong.techub.web.front.chat.ws;

import com.github.jakicdong.techub.web.front.chat.rest.SimpleChatgptHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
* @author JakicDong
* @description 基础版本的websocket长连接相关配置
* @time 2025/9/20 20:37
*/
//@Configuration
//@EnableWebSocket
public class SimpleWsConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler(), "/chatgpt")
                .setAllowedOrigins("*")
                .addInterceptors(new SimpleWsAuthInterceptor());
    }

    @Bean
    public WebSocketHandler chatWebSocketHandler() {
        return new SimpleChatgptHandler();
    }
}

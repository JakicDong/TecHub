package com.github.jakicdong.techub.web.front.chat.stomp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/*
* @author JakicDong
* @description STOMP配置类
* v1.1 stomp协议的websocket实现的chatgpt聊天方式 与 全局通知功能
* @time 2025/9/8 19:18
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker // 开启websocket代理 让你用“发布-订阅”的方式进行双向消息推送
public class WsChatConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 定义客户端与服务端通讯信息Broker(消息代理)
     * 这里定义的是客户端接收服务端消息的相关信息，如派聪明的回答： WsAnswerHelper#response 就是往 "/chat/rsp" 发送消息
     * 对应的前端订阅的也是 chat/index.html: stompClient.subscribe(`/user/chat/rsp`, xxx)
     *
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //配置消息代理
        // 开启一个简单的基于内存的消息代理，前缀是/user的将消息会转发给消息代理 broker
        // 然后再由消息代理，将消息广播给当前连接的客户端
        // /chat broker用于派聪明聊天； /msg broker用于服务端给用户推送消息
        config.enableSimpleBroker("/chat", "/msg");

        //配置消息转发
        // 表示配置一个或多个前缀，通过这些前缀过滤出需要被注解方法处理的消息。
        // 例如，前缀为 /app 的 destination 可以通过@MessageMapping注解的方法处理，
        // 而其他 destination （例如 /topic /queue）将被直接交给 broker 处理
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * 定义端点(用于连接 校验 拦截)
     * 添加一个服务端点，来接收客户端的连接
     * 即客户端创建ws时，指定的地址, chat/index.html: let socket = new WebSocket(`${protocol}//${host}/gpt/${session}/${aiType}`);
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个 /gpt/{id} 的 WebSocket endPoint; 其中 {id} 用于让用户连接终端时都可以有自己的路径
        // 作为 Principal 的标识，以便实现向指定用户发送信息
        // sockjs 可以解决浏览器对 WebSocket 的兼容性问题，
        registry.addEndpoint("/gpt/{id}/{aiType}", "/notify")
                .setHandshakeHandler(new AuthHandshakeHandler()) // 执行握手的初始化验证 标识用户身份
                .addInterceptors(new AuthHandshakeInterceptor()) // 添加验权拦截器
                // 注意下面这个，不要使用 setAllowedOrigins("*")，使用之后有啥问题可以实操验证一下
                // setAllowedOrigins接受一个字符串数组作为参数，每个元素代表一个允许访问的客户端地址，内部的值为具体的 "http://localhost:8080"
                // setAllowedOriginPatterns接受一个正则表达式数组作为参数，每个元素代表一个允许访问的客户端地址的模式, 内部值可以为正则，如 "*", "http://*:8080"
                .setAllowedOriginPatterns("*")
        ;
    }


    /**
     * 配置接收消息的拦截器
     *
     * 设置输出消息通道的线程数，默认线程为1，可以自己自定义线程数，最大线程数，线程存活时间
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor()
                .corePoolSize(4)
                .maxPoolSize(10)
                .keepAliveSeconds(60);
        registration.interceptors(channelInInterceptor());
    }

    /**
     * 配置返回消息的拦截器
     *
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(channelOutInterceptor());
    }

    @Bean
    public HandshakeHandler handshakeHandler() {
        // 握手处理器，用于初始化会话中的用户信息
        return new AuthHandshakeHandler();
    }

    @Bean
    public HttpSessionHandshakeInterceptor handshakeInterceptor() {
        // 握手拦截器, 用于身份验证识别
        return new AuthHandshakeInterceptor();
    }

    @Bean
    public ChannelInterceptor channelInInterceptor() {
        return new AuthInChannelInterceptor();
    }

    @Bean
    public ChannelInterceptor channelOutInterceptor() {
        return new AuthOutChannelInterceptor();
    }




}

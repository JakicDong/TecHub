package com.github.jakicdong.techub.core.rabbitmq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/*
* @author JakicDong
* @description rabbitmq连接类
* @time 2025/9/13 19:37
*/
@Slf4j
public class RabbitmqConnection {

    private Connection connection;
    private Channel channel;
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    public RabbitmqConnection(String host, int port, String userName, String password, String virtualhost) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualhost);
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            log.error("创建RabbitMQ连接失败", e);
            throw new RuntimeException("创建RabbitMQ连接失败", e);
        }
    }

    /**
     * 获取链接
     *
     * @return
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * 获取Channel
     *
     * @return
     */
    public Channel getChannel() {
        if (isClosed.get() || !channel.isOpen()) {
            throw new IllegalStateException("连接已关闭或Channel不可用");
        }
        return channel;
    }

    /**
     * 检查连接是否可用
     *
     * @return
     */
    public boolean isAvailable() {
        return !isClosed.get() && connection != null && connection.isOpen() && channel != null && channel.isOpen();
    }

    /**
     * 关闭链接
     *
     */
    public void close() {
        if (isClosed.compareAndSet(false, true)) {
            try {
                if (channel != null && channel.isOpen()) {
                    channel.close();
                }
            } catch (IOException | TimeoutException e) {
                log.warn("关闭Channel时发生异常", e);
            }
            
            try {
                if (connection != null && connection.isOpen()) {
                    connection.close();
                }
            } catch (IOException e) {
                log.warn("关闭Connection时发生异常", e);
            }
        }
    }
}

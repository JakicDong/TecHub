package com.github.jakicdong.techub.service.notify.service;


/*
* @author JakicDong
* @description RabbitMQ服务接口
* @time 2025/9/13 19:31
*/

import com.rabbitmq.client.BuiltinExchangeType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface RabbitmqService {
    boolean enabled();

    /**
     * 发布消息
     *
     * @param exchange
     * @param exchangeType
     * @param toutingKey
     * @param message
     * @throws IOException
     * @throws TimeoutException
     */
    void publishMsg(String exchange,
                    BuiltinExchangeType exchangeType,
                    String toutingKey,
                    String message);


    /**
     * 消费消息
     *
     * @param exchange
     * @param queue
     * @param routingKey
     * @throws IOException
     * @throws TimeoutException
     */
    void consumerMsg(String exchange,
                     String queue,
                     String routingKey) throws IOException, TimeoutException;


    void processConsumerMsg();
}


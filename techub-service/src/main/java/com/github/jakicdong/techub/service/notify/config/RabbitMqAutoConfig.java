package com.github.jakicdong.techub.service.notify.config;


import com.github.jakicdong.techub.core.async.AsyncUtil;
import com.github.jakicdong.techub.core.config.RabbitmqProperties;
import com.github.jakicdong.techub.core.rabbitmq.RabbitmqConnectionPool;
import com.github.jakicdong.techub.service.notify.service.RabbitmqService;
import com.github.jakicdong.techub.service.notify.service.impl.RabbitmqServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/*
* @author JakicDong
* @description RabbitMQ自动配置类
* @time 2025/9/13 17:02
*/

@Slf4j
@Configuration
@ConditionalOnProperty(value = "rabbitmq.switchFlag")
@EnableConfigurationProperties(RabbitmqProperties.class)
public class RabbitMqAutoConfig implements ApplicationRunner {
    @Resource
    private RabbitmqService rabbitmqService;

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            String host = rabbitmqProperties.getHost();
            Integer port = rabbitmqProperties.getPort();
            String userName = rabbitmqProperties.getUsername();
            String password = rabbitmqProperties.getPassport();
            String virtualhost = rabbitmqProperties.getVirtualhost();
            Integer poolSize = rabbitmqProperties.getPoolSize();
            
            log.info("开始初始化RabbitMQ连接池，配置: host={}, port={}, username={}, virtualhost={}, poolSize={}", 
                    host, port, userName, virtualhost, poolSize);
            
            RabbitmqConnectionPool.initRabbitmqConnectionPool(host, port, userName, password, virtualhost, poolSize);
            
            // 异步启动消费者
            AsyncUtil.execute(() -> {
                try {
                    rabbitmqService.processConsumerMsg();
                } catch (Exception e) {
                    log.error("启动RabbitMQ消费者失败", e);
                }
            });
            
            log.info("RabbitMQ自动配置完成");
        } catch (Exception e) {
            log.error("RabbitMQ自动配置失败", e);
            throw e;
        }
    }

    @PreDestroy
    public void destroy() {
        log.info("开始关闭RabbitMQ相关资源");
        try {
            // 停止消费者
            if (rabbitmqService instanceof RabbitmqServiceImpl) {
                ((RabbitmqServiceImpl) rabbitmqService).stopConsumer();
            }
            
            // 关闭连接池
            RabbitmqConnectionPool.close();
            
            log.info("RabbitMQ资源关闭完成");
        } catch (Exception e) {
            log.error("关闭RabbitMQ资源时发生异常", e);
        }
    }
}

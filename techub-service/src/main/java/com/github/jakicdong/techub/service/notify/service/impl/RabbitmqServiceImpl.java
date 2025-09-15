package com.github.jakicdong.techub.service.notify.service.impl;

import com.github.jakicdong.techub.api.model.enums.NotifyTypeEnum;
import com.github.jakicdong.techub.core.common.CommonConstants;
import com.github.jakicdong.techub.core.rabbitmq.RabbitmqConnection;
import com.github.jakicdong.techub.core.rabbitmq.RabbitmqConnectionPool;
import com.github.jakicdong.techub.core.util.JsonUtil;
import com.github.jakicdong.techub.core.util.SpringUtil;
import com.github.jakicdong.techub.service.notify.service.NotifyService;
import com.github.jakicdong.techub.service.notify.service.RabbitmqService;
import com.github.jakicdong.techub.service.user.repository.entity.UserFootDO;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/*
* @author JakicDong
* @description RabbitMQ服务实现类
* @time 2025/9/13 19:33
*/
/*
用户A                   连接池                   消费者                   线程池                   用户B
  |                      |                      |                        |                      |
  |--点赞---------------->|                      |                        |                      |
  |                      |--获取连接------------>|                        |                      |
  |                      |--发布消息------------>|                        |                      |
  |                      |--归还连接------------>|                        |                      |
  |                      |                      |                        |                      |
  |                      |<--获取连接------------|                        |                      |
  |                      |<--消费消息------------|                        |                      |
  |                      |                      |--提交任务-------------->|                      |
  |                      |                      |                        |--处理消息             |
  |                      |                      |                        |--保存数据库           |
  |                      |                      |                        |--WebSocket推送-------->|--收到通知
  |                      |                      |                        |                      |
  |                      |<--归还连接------------|                        |                      |

 */
@Slf4j
@Service
public class RabbitmqServiceImpl implements RabbitmqService {

    @Autowired
    private NotifyService notifyService;

    private final AtomicBoolean isConsumerRunning = new AtomicBoolean(false);
    private final AtomicBoolean shouldStop = new AtomicBoolean(false);
    
    // 消息处理线程池
    private ExecutorService messageProcessorPool;
    private int consumerCount; // 消费者数量
    private int processorThreads; // 消息处理线程数
    
    /**
     * 获取消费者数量（可配置）
     */
    private int getConsumerCount() {
        if (consumerCount == 0) {
            String count = SpringUtil.getConfig("rabbitmq.consumerCount");
            consumerCount = count != null ? Integer.parseInt(count) : 3;
        }
        return consumerCount;
    }
    
    /**
     * 获取处理线程数（可配置）
     */
    private int getProcessorThreads() {
        if (processorThreads == 0) {
            String threads = SpringUtil.getConfig("rabbitmq.processorThreads");
            processorThreads = threads != null ? Integer.parseInt(threads) : 10;
        }
        return processorThreads;
    }

    @Override
    public boolean enabled() {
        return "true".equalsIgnoreCase(SpringUtil.getConfig("rabbitmq.switchFlag"));
    }

    @Override
    public void publishMsg(String exchange,
                           BuiltinExchangeType exchangeType,
                           String routingKey,
                           String message) {
        RabbitmqConnection rabbitmqConnection = null;
        try {
            // 获取连接
            rabbitmqConnection = RabbitmqConnectionPool.getConnection();
            Channel channel = rabbitmqConnection.getChannel();
            
            // 声明exchange中的消息为可持久化，不自动删除
            channel.exchangeDeclare(exchange, exchangeType, true, false, null);
            
            // 发布消息
            channel.basicPublish(exchange, routingKey, null, message.getBytes());
            log.info("Publish msg: {}", message);
            
        } catch (InterruptedException e) {
            log.error("获取RabbitMQ连接被中断: exchange: {}, msg: {}", exchange, message, e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("rabbitMq消息发送异常: exchange: {}, msg: {}", exchange, message, e);
        } finally {
            if (rabbitmqConnection != null) {
                RabbitmqConnectionPool.returnConnection(rabbitmqConnection);
            }
        }
    }

    @Override
    public void consumerMsg(String exchange,
                            String queueName,
                            String routingKey) {
        // 这个方法现在只用于初始化消费者，实际消费在processConsumerMsg中处理
        log.info("consumerMsg方法被调用，但实际消费逻辑在processConsumerMsg中处理");
    }

    @Override
    public void processConsumerMsg() {
        if (!enabled()) {
            log.info("RabbitMQ未启用，跳过消费者启动");
            return;
        }

        if (isConsumerRunning.compareAndSet(false, true)) {
            log.info("开始启动RabbitMQ消费者");
            // 初始化消息处理线程池
            initMessageProcessorPool();
            // 启动多个消费者
            startMultipleConsumers();
        } else {
            log.warn("消费者已经在运行中");
        }
    }
    
    /**
     * 初始化消息处理线程池
     */
    private void initMessageProcessorPool() {
        int processorThreads = getProcessorThreads();
        messageProcessorPool = new ThreadPoolExecutor(
            processorThreads, // 核心线程数
            processorThreads, // 最大线程数
            60L, TimeUnit.SECONDS, // 空闲时间
            new LinkedBlockingQueue<>(1000), // 队列大小
            r -> {
                Thread t = new Thread(r, "rabbitmq-message-processor-" + System.currentTimeMillis());
                t.setDaemon(true);
                return t;
            }
        );
        log.info("消息处理线程池初始化完成，线程数: {}", processorThreads);
    }
    
    /**
     * 启动多个消费者
     */
    private void startMultipleConsumers() {
        int consumerCount = getConsumerCount();
        for (int i = 0; i < consumerCount; i++) {
            final int consumerIndex = i;
            // 每个消费者在独立线程中运行
            new Thread(() -> {
                try {
                    startConsumer(consumerIndex);
                } catch (Exception e) {
                    log.error("消费者 {} 启动失败", consumerIndex, e);
                }
            }, "rabbitmq-consumer-" + consumerIndex).start();
        }
    }

    /**
     * 启动消费者
     */
    private void startConsumer(int consumerIndex) {
        int retryCount = 0;
        final int maxRetries = 3;
        final long retryDelay = 5000; // 5秒

        while (!shouldStop.get() && retryCount < maxRetries) {
            RabbitmqConnection consumerConnection = null;
            try {
                // 获取专用连接用于消费
                consumerConnection = RabbitmqConnectionPool.getConnection();
                Channel channel = consumerConnection.getChannel();

                // 声明队列和绑定
                channel.queueDeclare(CommonConstants.QUERE_NAME_PRAISE, true, false, false, null);
                channel.queueBind(CommonConstants.QUERE_NAME_PRAISE, CommonConstants.EXCHANGE_NAME_DIRECT, 
                                 CommonConstants.QUERE_KEY_PRAISE);

                // 创建消费者
                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, 
                                             AMQP.BasicProperties properties, byte[] body) throws IOException {
                        // 将消息处理提交到线程池
                        messageProcessorPool.submit(() -> {
                            try {
                                String message = new String(body, "UTF-8");
                                log.info("Consumer[{}] processing msg: {}", consumerIndex, message);

                                // 处理消息
                                UserFootDO userFootDO = JsonUtil.toObj(message, UserFootDO.class);
                                notifyService.saveArticleNotify(userFootDO, NotifyTypeEnum.PRAISE);
                                
                                // 发送WebSocket弹窗通知（与Spring事件机制保持一致）
                                String notifyMsg = String.format("太棒了，您的%s 点赞数+1!!!",
                                        userFootDO.getDocumentType() == 1 ? "文章" : "评论");
                                notifyService.notifyToUser(userFootDO.getDocumentUserId(), notifyMsg);

                                // 手动确认消息
                                channel.basicAck(envelope.getDeliveryTag(), false);
                                
                            } catch (Exception e) {
                                try {
                                    log.error("处理消息时发生异常: {}", new String(body, "UTF-8"), e);
                                } catch (UnsupportedEncodingException ue) {
                                    log.error("处理消息时发生异常，且无法解析消息内容", e);
                                }
                                try {
                                    // 处理失败时拒绝消息，不重新入队
                                    channel.basicNack(envelope.getDeliveryTag(), false, false);
                                } catch (IOException ioException) {
                                    log.error("拒绝消息时发生异常", ioException);
                                }
                            }
                        });
                    }

                    @Override
                    public void handleCancel(String consumerTag) throws IOException {
                        log.warn("消费者被取消: {}", consumerTag);
                        isConsumerRunning.set(false);
                    }

                    @Override
                    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                        log.warn("消费者收到关闭信号: {}", consumerTag, sig);
                        isConsumerRunning.set(false);
                    }
                };

                // 开始消费，取消自动ack
                channel.basicConsume(CommonConstants.QUERE_NAME_PRAISE, false, consumer);
                
                log.info("RabbitMQ消费者启动成功");
                retryCount = 0; // 重置重试计数
                
                // 保持消费者运行
                while (!shouldStop.get() && isConsumerRunning.get()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.warn("消费者线程被中断");
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                
            } catch (Exception e) {
                retryCount++;
                log.error("启动消费者失败，重试次数: {}/{}", retryCount, maxRetries, e);
                
                if (retryCount < maxRetries) {
                    try {
                        Thread.sleep(retryDelay);
                    } catch (InterruptedException ie) {
                        log.warn("重试等待被中断");
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    log.error("达到最大重试次数，停止尝试启动消费者");
                    break;
                }
            } finally {
                // 清理资源
                cleanupConsumerResources();
            }
        }
        
        isConsumerRunning.set(false);
        log.info("RabbitMQ消费者已停止");
    }

    /**
     * 清理消费者资源
     */
    private void cleanupConsumerResources() {
        // 资源清理逻辑已移至各个消费者的finally块中
        log.info("消费者资源清理完成");
    }

    /**
     * 停止消费者
     */
    public void stopConsumer() {
        log.info("开始停止RabbitMQ消费者");
        shouldStop.set(true);
        isConsumerRunning.set(false);
        
        // 关闭消息处理线程池
        if (messageProcessorPool != null) {
            log.info("正在关闭消息处理线程池...");
            messageProcessorPool.shutdown();
            try {
                if (!messageProcessorPool.awaitTermination(30, TimeUnit.SECONDS)) {
                    log.warn("消息处理线程池未能在30秒内正常关闭，强制关闭");
                    messageProcessorPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                log.warn("等待线程池关闭被中断", e);
                messageProcessorPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
        cleanupConsumerResources();
    }

    @PreDestroy
    public void destroy() {
        log.info("RabbitmqServiceImpl正在销毁");
        stopConsumer();
    }
}
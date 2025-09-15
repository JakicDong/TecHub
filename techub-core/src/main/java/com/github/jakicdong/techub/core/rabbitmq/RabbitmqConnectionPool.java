package com.github.jakicdong.techub.core.rabbitmq;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/*
* @author JakicDong
* @description rabbitmq连接池
* @time 2025/9/13 20:10
*/
@Slf4j
public class RabbitmqConnectionPool {

    private static BlockingQueue<RabbitmqConnection> pool;
    private static String host;
    private static int port;
    private static String userName;
    private static String password;
    private static String virtualhost;
    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);

    /**
     * 初始化连接池
     *
     * @param host
     * @param port
     * @param userName
     * @param password
     * @param virtualhost
     * @param poolSize
     */
    public static void initRabbitmqConnectionPool(String host, int port, String userName, String password,
                                             String virtualhost,
                                           Integer poolSize) {
        if (isInitialized.compareAndSet(false, true)) {
            RabbitmqConnectionPool.host = host;
            RabbitmqConnectionPool.port = port;
            RabbitmqConnectionPool.userName = userName;
            RabbitmqConnectionPool.password = password;
            RabbitmqConnectionPool.virtualhost = virtualhost;
            
            pool = new LinkedBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                try {
                    pool.add(new RabbitmqConnection(host, port, userName, password, virtualhost));
                } catch (Exception e) {
                    log.error("初始化连接池时创建连接失败", e);
                }
            }
            log.info("RabbitMQ连接池初始化完成，池大小: {}", poolSize);
        }
    }

    /**
     * 获取连接
     *
     * @return
     * @throws InterruptedException
     */
    public static RabbitmqConnection getConnection() throws InterruptedException {
        if (!isInitialized.get()) {
            throw new IllegalStateException("连接池未初始化");
        }
        
        RabbitmqConnection connection = pool.take();
        
        // 检查连接是否可用，如果不可用则重新创建
        if (!connection.isAvailable()) {
            log.warn("获取到不可用的连接，重新创建");
            try {
                connection = new RabbitmqConnection(host, port, userName, password, virtualhost);
            } catch (Exception e) {
                log.error("重新创建连接失败", e);
                // 如果重新创建失败，尝试从池中获取其他连接
                return getConnection();
            }
        }
        
        return connection;
    }

    /**
     * 归还连接
     *
     * @param connection
     */
    public static void returnConnection(RabbitmqConnection connection) {
        if (connection != null && connection.isAvailable()) {
            pool.offer(connection);
        } else {
            // 如果连接不可用，尝试创建新连接补充到池中
            try {
                RabbitmqConnection newConnection = new RabbitmqConnection(host, port, userName, password, virtualhost);
                pool.offer(newConnection);
            } catch (Exception e) {
                log.error("补充连接池时创建连接失败", e);
            }
        }
    }

    /**
     * 关闭连接池
     */
    public static void close() {
        if (isInitialized.compareAndSet(true, false)) {
            log.info("开始关闭RabbitMQ连接池");
            pool.forEach(connection -> {
                try {
                    connection.close();
                } catch (Exception e) {
                    log.warn("关闭连接时发生异常", e);
                }
            });
            pool.clear();
            log.info("RabbitMQ连接池已关闭");
        }
    }

    /**
     * 获取连接池状态
     */
    public static int getPoolSize() {
        return pool != null ? pool.size() : 0;
    }
}

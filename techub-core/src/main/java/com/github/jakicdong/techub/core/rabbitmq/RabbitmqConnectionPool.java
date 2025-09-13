package com.github.jakicdong.techub.core.rabbitmq;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
* @author JakicDong
* @description rabbitmq连接池
* @time 2025/9/13 20:10
*/
public class RabbitmqConnectionPool {

    private static BlockingQueue<RabbitmqConnection> pool;
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
        pool = new LinkedBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            pool.add(new RabbitmqConnection(host, port, userName, password, virtualhost));
        }
    }
    /**
     * 获取连接
     *
     * @return
     * @throws InterruptedException
     */
    public static RabbitmqConnection getConnection() throws InterruptedException {
        return pool.take();
    }
    /**
     * 归还连接
     *
     * @param connection
     */
    public static void returnConnection(RabbitmqConnection connection) {
        pool.add(connection);
    }

    /**
     * 关闭连接池
     */
    public static void close() {
        pool.forEach(RabbitmqConnection::close);
    }
}

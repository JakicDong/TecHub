package com.github.jakicdong.techub.core.util;

/*
* @author JakicDong
* @description Socket工具类
* @time 2025/7/2 13:07
*/

import javax.net.ServerSocketFactory;
import java.net.ServerSocket;
import java.util.Random;

public class SocketUtil {

     //随机数
    private static Random random = new Random();

    /**
     * 判断端口是否可用
     */
    public static boolean isPortAvailable(int port) {
        try {
            ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(port, 1);
            serverSocket.close();
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    /**
     * 找一个随机的端口号
     */
    private static int findRandomPort(int minPort, int maxPort) {
        int portRange = maxPort - minPort;
        return minPort + random.nextInt(portRange + 1);
    }

    /**
     * 找一个可用的端口号
     */
    public static int findAvailableTcpPort(int minPort, int maxPort, int defaultPort) {
        if (isPortAvailable(defaultPort)) {
            return defaultPort;
        }
        if (maxPort <= minPort) {
            throw new IllegalArgumentException("maxPort should bigger than miPort!");
        }
        int portRange = maxPort - minPort;
        int searchCounter = 0;
        while (searchCounter <= portRange) {
            int candidatePort = findRandomPort(minPort, maxPort);
            ++searchCounter;
            if (isPortAvailable(candidatePort)) {
                return candidatePort;
            }
        }
        throw new IllegalStateException(String.format("Could not find an available %s port in the range [%d, %d] after %d attempts", SocketUtil.class.getName(), minPort, maxPort, searchCounter));
    }


}

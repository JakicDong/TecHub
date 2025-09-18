package com.github.jakicdong.techub.service.shortlink.help;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class ShortCodeGenerator {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE62_LENGTH = BASE62.length();
    private static final int HASH_LENGTH = 5;


    private static final Cache<String, Boolean> existingShortCodes = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    public static String generateShortCode(String longUrl) throws NoSuchAlgorithmException {
        String shortCode = generateHash(longUrl);
        final int MAX_ATTEMPTS = 10;
        int attempts = 0;
        while (existingShortCodes.getIfPresent(shortCode) != null) {
            if (attempts >= MAX_ATTEMPTS) {
                throw new RuntimeException("生成唯一短链接代码失败，已尝试 " + MAX_ATTEMPTS + " 次");
            }
            shortCode = generateHash(longUrl + System.nanoTime());
            attempts++;
        }

        existingShortCodes.put(shortCode, Boolean.TRUE);
        return shortCode;
    }

    /**
     * 生成短码的核心哈希映射方法
     * 将输入字符串通过SHA-256哈希算法转换为Base62编码的短码
     * 
     * @param input 输入字符串（通常是URL路径）
     * @return 生成的短码（5位Base62字符串）
     * @throws NoSuchAlgorithmException 如果SHA-256算法不可用
     */

    private static String generateHash(String input) throws NoSuchAlgorithmException {
        // 1. 创建SHA-256消息摘要实例
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // 2. 将输入字符串转换为字节数组并进行SHA-256哈希计算
        // 得到256位（32字节）的哈希值
        byte[] hash = md.digest(input.getBytes());
        
        
        // 3. 用于构建最终短码的字符串构建器
        StringBuilder hashString = new StringBuilder();

        // 4. 遍历哈希字节数组，生成指定长度的短码
        for (int i = 0; i < hash.length && hashString.length() < ShortCodeGenerator.HASH_LENGTH; i++) {
            // 4.1 将字节转换为无符号整数（0-255）
            // hash[i] & 0xFF 确保得到正数
            int index = (hash[i] & 0xFF) % BASE62_LENGTH;
            
            // 4.2 根据索引从Base62字符集中获取对应字符
            // Base62包含：0-9, A-Z, a-z 共62个字符
            hashString.append(BASE62.charAt(index));
        }

        // 5. 返回生成的短码字符串
        return hashString.toString();
    }

    public static void main(String[] args) {
        try {
            String longUrl = "http://example.com";
            String shortCode = generateShortCode(longUrl);
            System.out.println("The First Short code for " + longUrl + " is " + shortCode);

            String repeatShortCode = generateShortCode(longUrl);
            System.out.println("The Second Short code for " + longUrl + " is " + repeatShortCode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
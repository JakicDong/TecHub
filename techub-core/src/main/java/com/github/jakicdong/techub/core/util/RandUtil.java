package com.github.jakicdong.techub.core.util;


import java.util.Random;

/*
* @author JakicDong
* @description 随机工具类
* @time 2025/9/20 19:03
*/
public class RandUtil {
    private static Random random = new Random();
    private static final String txt = "0123456789qwertyuiopasdfghjklzxcvbnm";

    public static String random(int len) {
        StringBuilder builder = new StringBuilder();
        int size = txt.length();
        for (int i = 0; i < len; i++) {
            builder.append(txt.charAt(random.nextInt(size)));
        }
        return builder.toString();
    }

}

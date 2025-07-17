package com.github.jakicdong.techub.core.util;

/*
* @author JakicDong
* @description 数字工具类
* @time 2025/7/4 15:04
*/

public class NumUtil {
    public static boolean nullOrZero(Long num) {
        return num == null || num == 0L;
    }

    public static boolean nullOrZero(Integer num) {
        return num == null || num == 0;
    }

    public static boolean upZero(Long num) {
        return num != null && num > 0;
    }

    public static boolean upZero(Integer num) {
        return num != null && num > 0;
    }
}

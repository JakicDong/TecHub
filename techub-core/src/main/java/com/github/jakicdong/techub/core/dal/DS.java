package com.github.jakicdong.techub.core.dal;

/*
* @author JakicDong
* @description 数据源切换注解，用于切换数据源
* @time 2025/9/20 18:56
*/

public interface DS {
    /**
     * 使用的数据源名
     *
     * @return
     */
    String name();
}

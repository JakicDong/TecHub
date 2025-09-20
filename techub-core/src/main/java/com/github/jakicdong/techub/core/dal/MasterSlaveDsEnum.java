package com.github.jakicdong.techub.core.dal;

/*
* @author JakicDong
* @description 主从数据源枚举类，用于定义主从数据源的类型
* @time 2025/9/20 18:57
*/
public enum MasterSlaveDsEnum implements DS {
    /**
     * master主数据源类型
     */
    MASTER,
    /**
     * slave从数据源类型
     */
    SLAVE;
}

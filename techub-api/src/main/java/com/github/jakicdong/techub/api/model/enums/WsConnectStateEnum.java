package com.github.jakicdong.techub.api.model.enums;


/*
* @author JakicDong
* @description websocket 连接 状态
* @time 2025/9/11 16:32
*/
public enum WsConnectStateEnum {
    // 初始化
    INIT,
    // 连接中
    CONNECTING,
    // 已连接
    CONNECTED,
    // 连接失败
    FAILED,
    // 已关闭
    CLOSED,
    ;
}

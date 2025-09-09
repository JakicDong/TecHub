package com.github.jakicdong.techub.service.notify.service;

/*
* @author JakicDong
* @description 消息通知服务类
* @time 2025/7/4 17:08
*/

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public interface NotifyService {
    public static String NOTIFY_TOPIC = "/msg";

    /**
     * 查询用户未读消息数量
     *
     * @param userId
     * @return
     */
    int queryUserNotifyMsgCount(Long userId);

    /**
     * 通知用户
     *
     * @param userId
     * @param msg
     */
    void notifyToUser(Long userId, String msg);

    /**
     * 通知渠道维护
     *
     * @param accessor
     */
    void notifyChannelMaintain(StompHeaderAccessor accessor);
}

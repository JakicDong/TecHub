package com.github.jakicdong.techub.service.notify.service;

/*
* @author JakicDong
* @description 消息通知服务类
* @time 2025/7/4 17:08
*/

import com.github.jakicdong.techub.api.model.enums.NotifyTypeEnum;
import com.github.jakicdong.techub.api.model.vo.PageListVo;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.notify.dto.NotifyMsgDTO;
import com.github.jakicdong.techub.service.user.repository.entity.UserFootDO;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.Map;

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
     * 查询未读消息数
     *
     * @param userId
     * @return
     */
    Map<String, Integer> queryUnreadCounts(long userId);


    /**
     * 查询通知列表
     *
     * @param userId
     * @param type
     * @param page
     * @return
     */
    PageListVo<NotifyMsgDTO> queryUserNotices(Long userId, NotifyTypeEnum type, PageParam page);

    /**
     * 保存通知
     *
     * @param foot
     * @param notifyTypeEnum
     */
    void saveArticleNotify(UserFootDO foot, NotifyTypeEnum notifyTypeEnum);

    // -------------------------------------------- 下面是与用户的websocket长连接维护相关实现 -------------------------

    /**
     * ws: 给用户发送消息通知
     *
     * @param userId 用户id
     * @param msg    通知内容
     */
    void notifyToUser(Long userId, String msg);


    /**
     * ws: 维护与用户的长连接通道
     *
     * @param accessor
     */
    void notifyChannelMaintain(StompHeaderAccessor accessor);


}

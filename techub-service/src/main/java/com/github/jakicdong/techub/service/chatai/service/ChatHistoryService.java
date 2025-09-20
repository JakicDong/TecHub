package com.github.jakicdong.techub.service.chatai.service;

import com.github.jakicdong.techub.api.model.enums.ai.AISourceEnum;
import com.github.jakicdong.techub.api.model.vo.chat.ChatItemVo;
import com.github.jakicdong.techub.api.model.vo.chat.ChatSessionItemVo;

import java.util.List;


/*
* @author JakicDong
* @description 对话会话记录服务
* @time 2025/9/20 19:41
*/
public interface ChatHistoryService {
    /**
     * 获取对话列表
     *
     * @param source AI模型
     * @return
     */
    List<ChatSessionItemVo> listChatSessions(AISourceEnum source, Long userId);

    /**
     * 获取对话记录
     *
     * @param source AI模型
     * @param chatId 对话id
     * @param size   记录条数
     * @return 对话记录
     */
    List<ChatItemVo> listHistory(AISourceEnum source, Long userId, String chatId, Integer size);

    /**
     * 保存最新的一条对话内容
     *
     * @param source AI模型
     * @param chatId 对话id
     * @param item   对话内容
     */
    void saveRecord(AISourceEnum source, Long userId, String chatId, ChatItemVo item);


    Boolean updateChatSessionName(AISourceEnum source, String chatId, String title, Long userId);

    Boolean removeChatSession(AISourceEnum source, String chatId, Long userId);
}

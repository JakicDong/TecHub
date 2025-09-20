package com.github.jakicdong.techub.service.user.service;

/*
* @author JakicDong
* @description 用户AI相关服务
* @time 2025/7/9 15:07
*/

import com.github.jakicdong.techub.api.model.enums.ai.AISourceEnum;
import com.github.jakicdong.techub.api.model.vo.chat.ChatItemVo;
import com.github.jakicdong.techub.api.model.vo.user.UserPwdLoginReq;

public interface UserAiService {
    /**
     * 保存聊天历史记录
     *
     * @param source
     * @param user
     * @param item
     */
    void pushChatItem(AISourceEnum source, Long user, ChatItemVo item);

    /**
     * 获取用户的最大聊天次数
     *
     * @param userId
     * @return
     */
    int getMaxChatCnt(Long userId);

    /**
     * 重建用户绑定的星球编号
     *
     * @param loginReq
     */

    void initOrUpdateAiInfo(UserPwdLoginReq loginReq);
}

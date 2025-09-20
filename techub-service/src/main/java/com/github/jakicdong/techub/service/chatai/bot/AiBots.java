package com.github.jakicdong.techub.service.chatai.bot;

import com.github.jakicdong.techub.api.model.vo.chat.ChatItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


/*
* @author JakicDong
* @description  AI机器人
* @time 2025/9/20 19:39
*/
@Service
public class AiBots {
    @Autowired
    private HaterBot haterBot;

    /**
     * 判断目标用户是否为AI机器人
     *
     * @param userId
     * @return
     */
    public boolean aiBots(Long userId) {
        return Objects.equals(userId, haterBot.getBotUser().getUserId());
    }

    /**
     * 自动补齐AI机器人的提示词
     *
     * @param userId
     * @return
     */
    public ChatItemVo autoBuildPrompt(Long userId) {
        return haterBot.addPrompt(userId);
    }
}

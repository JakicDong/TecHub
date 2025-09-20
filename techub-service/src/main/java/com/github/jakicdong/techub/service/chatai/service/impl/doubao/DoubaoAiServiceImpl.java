package com.github.jakicdong.techub.service.chatai.service.impl.doubao;

import com.github.jakicdong.techub.api.model.enums.ai.AISourceEnum;
import com.github.jakicdong.techub.api.model.enums.ai.AISourceEnum;
import com.github.jakicdong.techub.api.model.enums.ai.AiChatStatEnum;
import com.github.jakicdong.techub.api.model.vo.chat.ChatItemVo;
import com.github.jakicdong.techub.api.model.vo.chat.ChatRecordsVo;
import com.github.jakicdong.techub.service.chatai.service.AbsChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;
/*
* @author JakicDong
* @description 豆包ai服务
* @time 2025/9/20 19:42
*/
@Slf4j
@Service
public class DoubaoAiServiceImpl extends AbsChatService {
    @Autowired
    private DoubaoIntegration doubaoIntegration;


    @Override
    public AiChatStatEnum doAnswer(Long user, ChatItemVo chat) {
        return doubaoIntegration.directAnswer(user, chat);
    }

    @Override
    public AiChatStatEnum doAsyncAnswer(Long user, ChatRecordsVo chatRes, BiConsumer<AiChatStatEnum, ChatRecordsVo> consumer) {
        return doubaoIntegration.streamAsyncAnswer(user, chatRes, consumer);
    }

    @Override
    public AISourceEnum source() {
        return AISourceEnum.DOU_BAO_AI;
    }

    @Override
    public boolean asyncFirst() {
        return true;
    }


}
package com.github.jakicdong.techub.service.chatai.service.impl.zhipu;

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
* @description 智普ai服务实现类
* @time 2025/9/20 19:41
*/
@Slf4j
@Service
public class ZhipuAiServiceImpl extends AbsChatService {

    @Autowired
    private ZhipuIntegration zhipuIntegration;

    @Override
    public AiChatStatEnum doAnswer(Long user, ChatItemVo chat) {
        if (zhipuIntegration.directReturn(user, chat)) {
            return AiChatStatEnum.END;
        }
        return AiChatStatEnum.ERROR;
    }

    @Override
    public AiChatStatEnum doAsyncAnswer(Long user, ChatRecordsVo chatRes, BiConsumer<AiChatStatEnum, ChatRecordsVo> consumer) {
        zhipuIntegration.streamReturn(user, chatRes, consumer);
        return AiChatStatEnum.IGNORE;
    }

    @Override
    public AISourceEnum source() {
        return AISourceEnum.ZHI_PU_AI;
    }

    @Override
    public boolean asyncFirst() {
        // true 表示优先使用异步返回； false 表示同步等待结果
        return true;
    }

}

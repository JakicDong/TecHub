package com.github.jakicdong.techub.service.user.service.conf;

/*
* @author JakicDong
* @description AI相关的配置
* @time 2025/7/9 14:12
*/

import com.github.jakicdong.techub.api.model.enums.ai.AISourceEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "ai")
public class AiConfig {

    @Data
    public static class AiMaxChatNumStrategyConf {
        /**
         * 默认的策略
         */
        private Integer basic;
        /**
         * 公众号用户 AI交互次数
         */
        private Integer wechat;
        /**
         * 星球用户 AI交互次数
         */
        private Integer star;

        // 星球最大编号
        private Integer starNumber;
        /**
         * 星球试用用户 AI交互次数
         */
        private Integer starTry;
        /**
         * 绑定了邀请者，再当前次数基础上新增的策略, 默认增加 10%
         */
        private Float invited;

        /**
         * 根据邀请的人数，增加的聊天次数策略，默认增加 20%
         */
        private Float inviteNum;

        /**
         * 多轮对话上下文的条数，默认最多给10条
         */
        private Integer historyContextCnt;
    }

    /**
     * 用户的最大使用次数配置项
     */
    private AiMaxChatNumStrategyConf maxNum;

    /**
     * 当前支持的AI模型
     */
    private List<AISourceEnum> source;

}

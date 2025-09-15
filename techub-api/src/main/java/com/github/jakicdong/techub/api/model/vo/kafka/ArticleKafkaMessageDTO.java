package com.github.jakicdong.techub.api.model.vo.kafka;

import lombok.Data;

/*
* @author JakicDong
* @description 文章—kafkaMessage
* @time 2025/9/15 20:45
*/
@Data
public class ArticleKafkaMessageDTO {

    /**
     * 点赞者
     */
    private String sourceUserName;

    /**
     * 被点赞ID
     */
    private Long targetUserId;

    /**
     * 文章名称
     */
    private String articleTitle;

    /**
     * 操作类型
     */
    private int type;

    /**
     * 操作类型名字
     */
    private String typeName;

}

package com.github.jakicdong.techub.service.user.repository.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jakicdong.techub.api.model.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/*
* @author JakicDong
* @description 用户主页的点赞数、收藏数、留言数、阅读数DO
* @time 2025/7/12 09:52
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_foot")
public class UserFootDO extends BaseDO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 文档ID（文章/评论）
     */
    private Long documentId;

    /**
     * 文档类型：1-文章，2-评论
     */
    private Integer documentType;
    /**
     * 发布该文档的用户ID
     */
    private Long documentUserId;
    /**
     * 收藏状态: 0-未收藏，1-已收藏
     */
    private Integer collectionStat;

    /**
     * 阅读状态: 0-未读，1-已读
     */
    private Integer readStat;

    /**
     * 评论状态: 0-未评论，1-已评论
     */
    private Integer commentStat;

    /**
     * 点赞状态: 0-未点赞，1-已点赞
     */
    private Integer praiseStat;



}

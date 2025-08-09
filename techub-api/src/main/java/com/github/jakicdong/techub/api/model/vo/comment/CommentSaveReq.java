package com.github.jakicdong.techub.api.model.vo.comment;

import lombok.Data;

/*
* @author JakicDong
* @description 评论列表入参
* @time 2025/8/9 20:25
*/

@Data
public class CommentSaveReq {

    /**
     * 评论ID
     */
    private Long commentId;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 父评论ID
     */
    private Long parentCommentId;

    /**
     * 顶级评论ID
     */
    private Long topCommentId;
}

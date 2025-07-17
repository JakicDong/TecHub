package com.github.jakicdong.techub.api.model.vo.user.dto;

import lombok.Data;

/*
* @author JakicDong
* @description 文章足迹计数
* @time 2025/7/3 14:34
*/
@Data
public class ArticleFootCountDTO {
    /**
     * 文章点赞数
     */
    private Integer  praiseCount;

    /**
     * 文章被阅读数
     */
    private Integer  readCount;

    /**
     * 文章被收藏数
     */
    private Integer  collectionCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    public ArticleFootCountDTO() {
        praiseCount = 0;
        readCount = 0;
        collectionCount = 0;
        commentCount = 0;
    }
}

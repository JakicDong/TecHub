package com.github.jakicdong.techub.api.model.vo.article.dto;

import com.github.jakicdong.techub.api.model.enums.column.ColumnTypeEnum;
import com.github.jakicdong.techub.api.model.vo.comment.dto.TopCommentDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;
import lombok.Data;

import java.util.List;

/*
* @author JakicDong
* @description 专栏文章dto
* @time 2025/9/6 13:37
*/
@Data
public class ColumnArticlesDTO {
    /**
     * 专栏详情
     */
    private Long column;

    /**
     * 当前查看的文章
     */
    private Integer section;

    /**
     * 文章详情
     */
    private ArticleDTO article;

    /**
     * 0 免费阅读
     * 1 要求登录阅读
     * 2 限时免费，若当前时间超过限时免费期，则调整为登录阅读
     *
     * @see ColumnTypeEnum#getType()
     */
    private Integer readType;

    /**
     * 文章评论
     */
    private List<TopCommentDTO> comments;

    /**
     * 热门评论
     */
    private TopCommentDTO hotComment;

    /**
     * 文章目录列表
     */
    private List<SimpleArticleDTO> articleList;

    // 翻页
    private ArticleOtherDTO other;

    // 赞赏用户列表
    private List<SimpleUserInfoDTO> payUsers;
}


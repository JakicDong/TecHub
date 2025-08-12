package com.github.jakicdong.techub.service.statistics.service;

/*
* @author JakicDong
* @description 技数统计相关
* @time 2025/7/3 20:28
*/

import com.github.jakicdong.techub.api.model.vo.user.dto.ArticleFootCountDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserStatisticInfoDTO;

public interface CountService {

    /**
     * 查询文章相关的统计信息
     *
     * @param articleId
     * @return 返回文章的 收藏、点赞、评论、阅读数
     */
    ArticleFootCountDTO queryArticleStatisticInfo(Long articleId);

    /**
     * 查询用户的相关统计信息
     *
     * @param userId
     * @return 返回用户的 收藏、点赞、文章、粉丝、关注，总的文章阅读数
     */
    UserStatisticInfoDTO queryUserStatisticInfo(Long userId);

    /**
     * 文章计数+1
     *
     * @param authorUserId 作者
     * @param articleId    文章
     * @return 计数器
     */
    void incrArticleReadCount(Long authorUserId, Long articleId);


    /**
     * 获取评论点赞数量
     *
     * @param commentId
     * @return
     */
    Long queryCommentPraiseCount(Long commentId);

}

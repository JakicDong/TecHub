package com.github.jakicdong.techub.service.comment.service;


/*
* @author JakicDong
* @description 评论Service接口
* @time 2025/8/11 19:42
*/

import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.comment.dto.TopCommentDTO;
import com.github.jakicdong.techub.service.comment.repository.entity.CommentDO;

import java.util.List;

public interface CommentReadService {


    /**
     * 根据评论id查询评论信息
     *
     * @param commentId
     * @return
     */
    CommentDO queryComment(Long commentId);

    /**
     * 查询文章评论列表
     *
     * @param articleId
     * @param page
     * @return
     */
    List<TopCommentDTO> getArticleComments(Long articleId, PageParam page);

    /**
     * 查询热门评论
     *
     * @param articleId
     * @return
     */
    TopCommentDTO queryHotComment(Long articleId);

    /**
     * 文章的有效评论数
     *
     * @param articleId
     * @return
     */
    int queryCommentCount(Long articleId);
}

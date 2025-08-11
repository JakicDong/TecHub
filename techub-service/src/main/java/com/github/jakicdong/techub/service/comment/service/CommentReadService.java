package com.github.jakicdong.techub.service.comment.service;


/*
* @author JakicDong
* @description 评论Service接口
* @time 2025/8/11 19:42
*/

import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.comment.dto.TopCommentDTO;

import java.util.List;

public interface CommentReadService {



    /**
     * 查询文章评论列表
     *
     * @param articleId
     * @param page
     * @return
     */
    List<TopCommentDTO> getArticleComments(Long articleId, PageParam page);
}

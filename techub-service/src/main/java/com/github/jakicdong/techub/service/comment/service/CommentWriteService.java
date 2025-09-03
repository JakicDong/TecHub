package com.github.jakicdong.techub.service.comment.service;

import com.github.jakicdong.techub.api.model.vo.comment.CommentSaveReq;

/*
* @author JakicDong
* @description 评论的写服务
* @time 2025/9/3 11:38
*/
public interface CommentWriteService {

    /**
     * 更新/保存评论
     *
     * @param commentSaveReq
     * @return
     */
    Long saveComment (CommentSaveReq commentSaveReq);
}

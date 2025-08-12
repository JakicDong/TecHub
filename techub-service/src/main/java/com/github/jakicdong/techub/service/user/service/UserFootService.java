package com.github.jakicdong.techub.service.user.service;

/*
* @author JakicDong
* @description 用户足迹Service接口
* @time 2025/7/3 20:25
*/

import com.github.jakicdong.techub.api.model.enums.DocumentTypeEnum;
import com.github.jakicdong.techub.api.model.enums.OperateTypeEnum;
import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserFootStatisticDTO;
import com.github.jakicdong.techub.service.user.repository.entity.UserFootDO;

import java.util.List;

public interface UserFootService {


    //获取用户主页的点赞数、收藏数、留言数、阅读数
    UserFootStatisticDTO getFootCount();

    /**
     * 保存或更新状态信息
     *
     * @param documentType    文档类型：博文 + 评论
     * @param documentId      文档id
     * @param authorId        作者
     * @param userId          操作人
     * @param operateTypeEnum 操作类型：点赞，评论，收藏等
     * @return
     */
    UserFootDO saveOrUpdateUserFoot(DocumentTypeEnum documentType, Long documentId, Long authorId, Long userId, OperateTypeEnum operateTypeEnum);


    /**
     * 查询文章的点赞用户信息
     *
     * @param articleId
     * @return
     */
    List<SimpleUserInfoDTO> queryArticlePraisedUsers(Long articleId);

    /**
     * 查询用户记录，用于判断是否点过赞、是否评论、是否收藏过
     *
     * @param documentId
     * @param type
     * @param userId
     * @return
     */
    UserFootDO queryUserFoot(Long documentId, Integer type, Long userId);

}

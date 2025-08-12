package com.github.jakicdong.techub.service.user.repository.dao;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.enums.DocumentTypeEnum;
import com.github.jakicdong.techub.api.model.enums.PraiseStatEnum;
import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserFootStatisticDTO;
import com.github.jakicdong.techub.service.user.repository.entity.UserFootDO;
import com.github.jakicdong.techub.service.user.repository.mapper.UserFootMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserFootDao extends ServiceImpl<UserFootMapper, UserFootDO> {





    //获取用户主页的点赞数、收藏数、留言数、阅读数
    public UserFootStatisticDTO getFootCount(){
        return baseMapper.getFootCount();
    }

    //根据文档id和用户id获取用户对文档的操作记录
    public UserFootDO getByDocumentAndUserId(Long documentId, Integer type, Long userId) {
        LambdaQueryWrapper<UserFootDO> query = Wrappers.lambdaQuery();
        query.eq(UserFootDO::getDocumentId, documentId)
                .eq(UserFootDO::getDocumentType, type)
                .eq(UserFootDO::getUserId, userId);
        return baseMapper.selectOne(query);
    }

    public List<SimpleUserInfoDTO> listDocumentPraisedUsers(Long documentId, Integer type, int size) {
        return baseMapper.listSimpleUserInfosByArticleId(documentId, type, size);
    }

    //查询评论的点赞数量
    public Long countCommentPraise(Long commentId){
        return lambdaQuery()
                .eq(UserFootDO::getDocumentId, commentId)
                .eq(UserFootDO::getDocumentType, DocumentTypeEnum.COMMENT.getCode())
                .eq(UserFootDO::getPraiseStat, PraiseStatEnum.PRAISE.getCode())
                .count();

    }


}

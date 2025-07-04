package com.github.jakicdong.techub.service.user.repository.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.enums.FollowStateEnum;
import com.github.jakicdong.techub.service.user.repository.entity.UserRelationDO;
import com.github.jakicdong.techub.service.user.repository.mapper.UserRelationMapper;
import org.springframework.stereotype.Repository;

/*
* @author JakicDong
* @description 用户关系DAO
* @time 2025/7/4 09:31
*/
@Repository
public class UserRelationDao  extends ServiceImpl<UserRelationMapper, UserRelationDO> {



    /**
     * 获取关注信息
     *
     * @param userId       登录用户
     * @param followUserId 关注的用户
     * @return
     */
    public UserRelationDO getUserRelationByUserId(Long userId, Long followUserId) {
        QueryWrapper<UserRelationDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(UserRelationDO::getUserId, userId)
                .eq(UserRelationDO::getFollowUserId, followUserId)
                .eq(UserRelationDO::getFollowState, FollowStateEnum.FOLLOW.getCode());
        return baseMapper.selectOne(queryWrapper);
    }
}

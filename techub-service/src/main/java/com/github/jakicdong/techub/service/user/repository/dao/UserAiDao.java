package com.github.jakicdong.techub.service.user.repository.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.enums.YesOrNoEnum;
import com.github.jakicdong.techub.service.user.repository.entity.UserAiDO;
import com.github.jakicdong.techub.service.user.repository.mapper.UserAiMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserAiDao extends ServiceImpl<UserAiMapper, UserAiDO> {
    @Resource
    private UserAiMapper userAiMapper;

    @Resource
    private UserDao userDao;

    public UserAiDO getByUserId(Long userId) {

        LambdaQueryWrapper<UserAiDO> queryUserAi = Wrappers.lambdaQuery();

        queryUserAi.eq(UserAiDO::getUserId, userId)
                .eq(UserAiDO::getDeleted, YesOrNoEnum.NO.getCode());
        return userAiMapper.selectOne(queryUserAi);
    }

    /**
     * 根据星球编号反查用户
     *
     * @param starNumber
     * @return
     */
    public UserAiDO getByStarNumber(String starNumber) {
        LambdaQueryWrapper<UserAiDO> queryUserAi = Wrappers.lambdaQuery();

        queryUserAi.eq(UserAiDO::getStarNumber, starNumber)
                .eq(UserAiDO::getDeleted, YesOrNoEnum.NO.getCode())
                .last("limit 1");
        return userAiMapper.selectOne(queryUserAi);
    }

    /**
     * 根据邀请码，查找对应的邀请人
     *
     * @param inviteCode 邀请码
     * @return
     */
    public UserAiDO getByInviteCode(String inviteCode) {
        LambdaQueryWrapper<UserAiDO> queryUserAi = Wrappers.lambdaQuery();

        queryUserAi.eq(UserAiDO::getInviteCode, inviteCode)
                .eq(UserAiDO::getDeleted, YesOrNoEnum.NO.getCode());
        return userAiMapper.selectOne(queryUserAi);
    }

}

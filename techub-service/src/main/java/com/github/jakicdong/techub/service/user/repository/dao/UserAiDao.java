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

}

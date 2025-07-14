package com.github.jakicdong.techub.service.user.repository.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.enums.YesOrNoEnum;
import com.github.jakicdong.techub.service.user.repository.entity.UserDO;
import com.github.jakicdong.techub.service.user.repository.entity.UserInfoDO;
import com.github.jakicdong.techub.service.user.repository.mapper.UserInfoMapper;
import com.github.jakicdong.techub.service.user.repository.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Repository
public class UserDao extends ServiceImpl<UserInfoMapper, UserInfoDO> {

    @Resource
    private UserMapper userMapper;

    public List<UserInfoDO> getByUserIds(Collection<Long> userIds) {
        LambdaQueryWrapper<UserInfoDO> query = Wrappers.lambdaQuery();
        query.in(UserInfoDO::getUserId, userIds)
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode());
        return baseMapper.selectList(query);
    }

    public UserInfoDO getByUserId(Long userId) {
        LambdaQueryWrapper<UserInfoDO> query = Wrappers.lambdaQuery();
        query.eq(UserInfoDO::getUserId, userId)
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode());
        return baseMapper.selectOne(query);
    }

    public UserDO getUserByUserName(String userName) {
        LambdaQueryWrapper<UserDO> queryUser = Wrappers.lambdaQuery();
        queryUser.eq(UserDO::getUserName, userName)
                .eq(UserDO::getDeleted, YesOrNoEnum.NO.getCode())
                .last("limit 1");
        return userMapper.selectOne(queryUser);
    }

    public void saveUser(UserDO user) {
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.updateById(user);
        }
    }

    public UserDO getUserByUserId(Long userId) {
        return userMapper.selectById(userId);
    }

    public Long getUserCount(){
        return lambdaQuery()
                .eq(UserInfoDO::getDeleted, YesOrNoEnum.NO.getCode())
                .count();
    }

}

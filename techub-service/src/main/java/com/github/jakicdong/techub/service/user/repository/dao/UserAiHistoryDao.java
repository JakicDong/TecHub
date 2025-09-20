package com.github.jakicdong.techub.service.user.repository.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.service.user.repository.entity.UserAiHistoryDO;
import com.github.jakicdong.techub.service.user.repository.mapper.UserAiHistoryMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/*
* @author JakicDong
* @description 用户AI对话历史相关的数据库操作
* @time 2025/9/20 20:20
*/
@Repository
public class UserAiHistoryDao extends ServiceImpl<UserAiHistoryMapper, UserAiHistoryDO> {

    @Resource
    private UserAiHistoryMapper userAiHistoryMapper;
}


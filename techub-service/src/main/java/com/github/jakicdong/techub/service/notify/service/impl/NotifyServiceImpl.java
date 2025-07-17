package com.github.jakicdong.techub.service.notify.service.impl;

import com.github.jakicdong.techub.api.model.enums.NotifyStatEnum;
import com.github.jakicdong.techub.service.notify.repository.dao.NotifyMsgDao;
import com.github.jakicdong.techub.service.notify.service.NotifyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotifyServiceImpl implements NotifyService {


    @Resource
    private NotifyMsgDao notifyMsgDao;

    @Override
    public int queryUserNotifyMsgCount(Long userId) {


        return notifyMsgDao.countByUserIdAndStat(userId, NotifyStatEnum.UNREAD.getStat());
    }
}

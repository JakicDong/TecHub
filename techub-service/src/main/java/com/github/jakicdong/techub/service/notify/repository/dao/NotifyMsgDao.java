package com.github.jakicdong.techub.service.notify.repository.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.service.notify.repository.entity.NotifyMsgDO;
import com.github.jakicdong.techub.service.notify.repository.mapper.NotifyMsgMapper;
import org.springframework.stereotype.Repository;

@Repository
public class NotifyMsgDao extends ServiceImpl<NotifyMsgMapper, NotifyMsgDO> {

    /**
     * 查询用户的消息通知数量
     *
     * @param userId
     * @return
     */
    public int countByUserIdAndStat(long userId, Integer stat) {
        return lambdaQuery()
                .eq(NotifyMsgDO::getNotifyUserId, userId)
                .eq(stat != null, NotifyMsgDO::getState, stat)
                .count().intValue();
    }
}

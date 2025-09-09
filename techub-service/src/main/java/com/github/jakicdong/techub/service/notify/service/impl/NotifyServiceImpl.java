package com.github.jakicdong.techub.service.notify.service.impl;

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.enums.NotifyStatEnum;
import com.github.jakicdong.techub.core.ws.WebSocketResponseUtil;
import com.github.jakicdong.techub.service.notify.repository.dao.NotifyMsgDao;
import com.github.jakicdong.techub.service.notify.service.NotifyService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {


    @Resource
    private NotifyMsgDao notifyMsgDao;

    /**
     * 记录用户与对应的jwt token之间的缓存关系；用于websocket的广播通知
     */
    private LoadingCache<Long, Set<String>> wsUserSessionCache;

    @PostConstruct
    public void init() {
        wsUserSessionCache = CacheBuilder.newBuilder()
                .maximumSize(500)
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build(new CacheLoader<Long, Set<String>>() {
                    @Override
                    public Set<String> load(Long aLong) throws Exception {
                        return new HashSet<>();
                    }
                });
    }

    @Override
    public int queryUserNotifyMsgCount(Long userId) {
        return notifyMsgDao.countByUserIdAndStat(userId, NotifyStatEnum.UNREAD.getStat());
    }




    // -------------------------------------------- 下面是与用户的websocket长连接维护相关实现 -------------------------

    /**
     * x用户发送
     * @param userId 用户id
     * @param msg 通知内容
     */
    @Override
    public void notifyToUser(Long userId, String msg) {
        wsUserSessionCache.getUnchecked(userId).forEach(s -> {
            WebSocketResponseUtil.sendMsgToUser(s, NOTIFY_TOPIC, msg);
        });
    }

    /**
     * 用户建立连接时，添加用户信息
     *
     * @param userId  用户id
     * @param session jwt token
     */
    private void addUserToken(Long userId, String session) {
        wsUserSessionCache.getUnchecked(userId).add(session);
    }

    /**
     * 断开连接时，移除用户信息
     *
     * @param userId  用户id
     * @param session jwt token
     */
    private void releaseUserToken(Long userId, String session) {
        wsUserSessionCache.getUnchecked(userId).remove(session);
    }

    /**
     * WebSocket通道管理
     *
     * @param accessor
     */
    @Override
    public void notifyChannelMaintain(StompHeaderAccessor accessor) {
        String destination = accessor.getDestination();
        if (StringUtils.isBlank(destination) || accessor.getCommand() == null) {
            return;
        }


        // 全局私信、通知长连接入口
        ReqInfoContext.ReqInfo user = (ReqInfoContext.ReqInfo) accessor.getUser();
        if (user == null) {
            log.info("websocket用户未登录! {}", accessor);
            return;
        }
        switch (accessor.getCommand()) {
            case SUBSCRIBE:
                // 建立用户通信通道
                addUserToken(user.getUserId(), user.getSession());
                break;
            case DISCONNECT:
                // 中断链接，去掉用户的长连接会话
                releaseUserToken(user.getUserId(), user.getSession());
                break;
        }
    }

}

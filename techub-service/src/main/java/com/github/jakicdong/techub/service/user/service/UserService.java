package com.github.jakicdong.techub.service.user.service;


import com.github.jakicdong.techub.api.model.vo.user.dto.BaseUserInfoDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserStatisticInfoDTO;

import java.util.Collection;
import java.util.List;

/*
* @author JakicDong
* @description 用户相关服务
* @time 2025/7/3 17:46
*/
public interface UserService {

    /**
     * 批量查询用户基本信息
     *
     * @param userIds
     * @return
     */
    List<SimpleUserInfoDTO> batchQuerySimpleUserInfo(Collection<Long> userIds);


    /**
     * 查询用户基本信息
     * todo: 可以做缓存优化
     *
     * @param userId
     * @return
     */
    BaseUserInfoDTO queryBasicUserInfo(Long userId);

    /**
     * 查询用户主页信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    UserStatisticInfoDTO queryUserInfoWithStatistic(Long userId);

    /**
     * 获取登录的用户信息,并更行丢对应的ip信息
     *
     * @param session  用户会话
     * @param clientIp 用户最新的登录ip
     * @return 返回用户基本信息
     */
    BaseUserInfoDTO getAndUpdateUserIpInfoBySessionId(String session, String clientIp);



}

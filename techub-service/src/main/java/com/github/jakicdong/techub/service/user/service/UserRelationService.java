package com.github.jakicdong.techub.service.user.service;


import com.github.jakicdong.techub.api.model.vo.PageListVo;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.user.dto.FollowUserInfoDTO;

import java.util.List;
import java.util.Set;

/*
* @author JakicDong
* @description 用户关系服务
* @time 2025/9/6 14:25
*/
public interface UserRelationService {


    /**
     * 我关注的用户
     *
     * @param userId
     * @param pageParam
     * @return
     */
    PageListVo<FollowUserInfoDTO> getUserFollowList(Long userId, PageParam pageParam);

    /**
     * 关注我的粉丝
     *
     * @param userId
     * @param pageParam
     * @return
     */
    PageListVo<FollowUserInfoDTO> getUserFansList(Long userId, PageParam pageParam);

    /**
     * 更新当前登录用户与列表中的用户的关注关系
     *
     * @param followList
     * @param loginUserId
     */
    void updateUserFollowRelationId(PageListVo<FollowUserInfoDTO> followList, Long loginUserId);

    /**
     * 根据登录用户从给定用户列表中，找出已关注的用户id
     *
     * @param userIds
     * @param loginUserId
     * @return
     */
    Set<Long> getFollowedUserId(List<Long> userIds, Long loginUserId);


}

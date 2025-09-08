package com.github.jakicdong.techub.service.user.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.user.dto.FollowUserInfoDTO;
import com.github.jakicdong.techub.service.user.repository.entity.UserRelationDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* @author JakicDong
* @description 用户关系Mapper
* @time 2025/7/4 10:07
*/
public interface UserRelationMapper extends BaseMapper<UserRelationDO> {

    /**
     * 我关注的用户
     * @param followUserId
     * @param pageParam
     * @return
     */
    List<FollowUserInfoDTO> queryUserFollowList(@Param("followUserId") Long followUserId, @Param("pageParam") PageParam pageParam);

    /**
     * 关注我的粉丝
     * @param userId
     * @param pageParam
     * @return
     */
    List<FollowUserInfoDTO> queryUserFansList(@Param("userId") Long userId, @Param("pageParam") PageParam pageParam);
}

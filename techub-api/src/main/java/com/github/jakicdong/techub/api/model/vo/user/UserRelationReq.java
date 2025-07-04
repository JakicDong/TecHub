package com.github.jakicdong.techub.api.model.vo.user;

import lombok.Data;

/*
* @author JakicDong
* @description 用户关系入参
* @time 2025/7/3 19:59
*/

@Data
public class UserRelationReq {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 粉丝用户ID
     */
    private Long followUserId;

    /**
     * 是否关注当前用户
     */
    private Boolean followed;
}

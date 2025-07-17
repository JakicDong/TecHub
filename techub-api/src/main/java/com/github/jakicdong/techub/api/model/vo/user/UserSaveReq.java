package com.github.jakicdong.techub.api.model.vo.user;

/*
* @author JakicDong
* @description 用户入参
* @time 2025/7/3 19:50
*/

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserSaveReq {
    /**
     * 主键ID
     */
    private Long userId;

    /**
     * 第三方用户ID
     */
    private String thirdAccountId;

    /**
     * 登录方式: 0-微信登录，1-账号密码登录
     */
    private Integer loginType;
}
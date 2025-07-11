package com.github.jakicdong.techub.api.model.vo.user;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/*
* @author JakicDong
* @description 基于用户名密码登录的相关请求信息
* @time 2025/7/8 14:22
*/
@Data
@Accessors(chain = true)
public class UserPwdLoginReq implements Serializable {
    private static final long serialVersionUID = 2139742660700910738L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 星球编号
     */
    private String starNumber;

    /**
     * 邀请码
     */
    private String invitationCode;
}
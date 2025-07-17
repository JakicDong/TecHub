package com.github.jakicdong.techub.api.model.enums.user;

/*
* @author JakicDong
* @description 登录方式枚举
* @time 2025/7/10 11:22
*/

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
    /**
     * 微信登录
     */
    WECHAT(0),
    /**
     * 用户名+密码登录
     */
    USER_PWD(1),
    ;
    private int type;
}


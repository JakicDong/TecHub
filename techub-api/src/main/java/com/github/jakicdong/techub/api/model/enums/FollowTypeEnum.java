package com.github.jakicdong.techub.api.model.enums;

/*
* @author JakicDong
* @description 关注类型枚举
* @time 2025/9/6 15:21
*/

import lombok.Getter;

@Getter
public enum FollowTypeEnum {

    FOLLOW("follow", "我关注的用户"),
    FANS("fans", "关注我的粉丝");

    FollowTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;

    public static FollowTypeEnum formCode(String code) {
        for (FollowTypeEnum value : FollowTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return FollowTypeEnum.FOLLOW;
    }
}

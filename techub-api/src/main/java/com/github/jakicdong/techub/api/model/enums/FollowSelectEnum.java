package com.github.jakicdong.techub.api.model.enums;

import lombok.Getter;

/*
* @author JakicDong
* @description 关注用户枚举
* @time 2025/9/6 15:12
*/

@Getter
public enum FollowSelectEnum {

    FOLLOW("follow", "关注列表"),
    FANS("fans", "粉丝列表");

    FollowSelectEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;

    public static FollowSelectEnum fromCode(String code) {
        for (FollowSelectEnum value : FollowSelectEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return FollowSelectEnum.FOLLOW;
    }
}

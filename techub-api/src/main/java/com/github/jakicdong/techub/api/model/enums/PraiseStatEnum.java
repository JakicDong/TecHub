package com.github.jakicdong.techub.api.model.enums;

import lombok.Getter;

/*
* @author JakicDong
* @description 点赞状态枚举
* @time 2025/7/12 10:53
*/
@Getter
public enum PraiseStatEnum {

    NOT_PRAISE(0, "未点赞"),
    PRAISE(1, "已点赞"),
    CANCEL_PRAISE(2, "取消点赞");

    PraiseStatEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;
    private final String desc;

    public static PraiseStatEnum formCode(Integer code) {
        for (PraiseStatEnum value : PraiseStatEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return PraiseStatEnum.NOT_PRAISE;
    }
}

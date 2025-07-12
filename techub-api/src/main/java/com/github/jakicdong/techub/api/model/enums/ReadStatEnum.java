package com.github.jakicdong.techub.api.model.enums;

import lombok.Getter;


/*
* @author JakicDong
* @description 阅读状态枚举
* @time 2025/7/12 10:33
*/

@Getter
public enum ReadStatEnum {

    NOT_READ(0, "未读"),
    READ(1, "已读");

    ReadStatEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;
    private final String desc;

    public static ReadStatEnum formCode(Integer code) {
        for (ReadStatEnum value : ReadStatEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return ReadStatEnum.NOT_READ;
    }
}

package com.github.jakicdong.techub.api.model.enums;

import lombok.Getter;

/*
* @author JakicDong
* @description 标签类型枚举
* @time 2025/9/11 16:33
*/
@Getter
public enum TagTypeEnum {

    SYSTEM_TAG(1, "系统标签"),
    CUSTOM_TAG(2, "已读");

    TagTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;
    private final String desc;

    public static TagTypeEnum formCode(Integer code) {
        for (TagTypeEnum value : TagTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return TagTypeEnum.SYSTEM_TAG;
    }
}

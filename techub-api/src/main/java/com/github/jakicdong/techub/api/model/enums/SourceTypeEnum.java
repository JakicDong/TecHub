package com.github.jakicdong.techub.api.model.enums;

import lombok.Getter;

/*
* @author JakicDong
* @description 文档来源枚举
* @time 2025/7/3 14:11
*/
@Getter
public enum SourceTypeEnum {
    EMPTY(0, ""),
    REPRINT(1, "转载"),
    ORIGINAL(2, "原创"),
    TRANSLATION(3, "翻译");

    SourceTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;
    private final String desc;

    public static SourceTypeEnum formCode(Integer code) {
        for (SourceTypeEnum value : SourceTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SourceTypeEnum.EMPTY;
    }

}

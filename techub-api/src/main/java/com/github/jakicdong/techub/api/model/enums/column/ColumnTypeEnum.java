package com.github.jakicdong.techub.api.model.enums.column;

/*
* @author JakicDong
* @description 发布类型枚举
* @time 2025/7/14 17:02
*/

import lombok.Getter;

@Getter
public enum ColumnTypeEnum {

    FREE(0, "免费"),
    LOGIN(1, "登录阅读"),
    TIME_FREE(2, "限时免费"),
    STAR_READ(3, "星球阅读"),
    ;

    ColumnTypeEnum(int code, String desc) {
        this.type = code;
        this.desc = desc;
    }

    private final int type;
    private final String desc;

    public static ColumnTypeEnum formCode(int code) {
        for (ColumnTypeEnum status : values()) {
            if (status.getType() == code) {
                return status;
            }
        }
        return ColumnTypeEnum.FREE;
    }
}

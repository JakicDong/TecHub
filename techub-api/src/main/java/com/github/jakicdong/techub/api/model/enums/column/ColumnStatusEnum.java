package com.github.jakicdong.techub.api.model.enums.column;


/*
* @author JakicDong
* @description 发布状态枚举
* @time 2025/7/14 17:01
*/

import lombok.Getter;

@Getter
public enum ColumnStatusEnum {

    OFFLINE(0, "未发布"),
    CONTINUE(1, "连载"),
    OVER(2, "已完结");

    ColumnStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final int code;
    private final String desc;

    public static ColumnStatusEnum formCode(int code) {
        for (ColumnStatusEnum status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return ColumnStatusEnum.OFFLINE;
    }
}
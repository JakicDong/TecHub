package com.github.jakicdong.techub.api.model.enums;

import lombok.Getter;

/*
* @author JakicDong
* @description 发布状态枚举
* @time 2025/7/3 12:09
*/
@Getter
public enum PushStatusEnum {
    OFFLINE(0, "未发布"),
    ONLINE(1,"已发布"),
    REVIEW(2, "审核");

    PushStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final int code;
    private final String desc;

    public static PushStatusEnum formCode(int code) {
        for (PushStatusEnum yesOrNoEnum : PushStatusEnum.values()) {
            if (yesOrNoEnum.getCode() == code) {
                return yesOrNoEnum;
            }
        }
        return PushStatusEnum.OFFLINE;
    }
}

package com.github.jakicdong.techub.api.model.enums;


import lombok.Getter;

/*
* @author JakicDong
* @description 官方状态枚举
* @time 2025/7/3 20:18
*/

@Getter
public enum OfficalStatEnum {

    NOT_OFFICAL(0, "非官方"),
    OFFICAL(1, "官方");

    OfficalStatEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;
    private final String desc;

    public static OfficalStatEnum formCode(Integer code) {
        for (OfficalStatEnum value : OfficalStatEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return OfficalStatEnum.NOT_OFFICAL;
    }
}

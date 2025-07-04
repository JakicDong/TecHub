package com.github.jakicdong.techub.api.model.enums;

/*
* @author JakicDong
* @description 配置类型枚举
* @time 2025/7/3 18:53
*/

import lombok.Getter;

@Getter
public enum ConfigTypeEnum {

    EMPTY(0, ""),
    HOME_PAGE(1, "首页Banner"),
    SIDE_PAGE(2, "侧边Banner"),
    ADVERTISEMENT(3, "广告Banner"),
    NOTICE(4, "公告"),
    COLUMN(5, "教程"),
    PDF(6, "电子书");

    ConfigTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final Integer code;
    private final String desc;

    public static ConfigTypeEnum formCode(Integer code) {
        for (ConfigTypeEnum value : ConfigTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return ConfigTypeEnum.EMPTY;
    }
}


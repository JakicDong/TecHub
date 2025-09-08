package com.github.jakicdong.techub.api.model.enums;

import lombok.Getter;

/*
* @author JakicDong
* @description 用户选择界面枚举
* @time 2025/9/6 15:18
*/
@Getter
public enum HomeSelectEnum {

    ARTICLE("article", "文章"),
    READ("read", "浏览记录"),
    FOLLOW("follow", "关注"),
    COLLECTION("collection", "收藏");

    HomeSelectEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;

    public static HomeSelectEnum fromCode(String code) {
        for (HomeSelectEnum value : HomeSelectEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return HomeSelectEnum.ARTICLE;
    }
}

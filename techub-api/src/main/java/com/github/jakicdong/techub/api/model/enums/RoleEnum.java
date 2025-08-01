package com.github.jakicdong.techub.api.model.enums;

import lombok.Getter;

import java.util.Objects;

/*
* @author JakicDong
* @description 用户角色枚举
* @time 2025/7/3 19:56
*/
public enum RoleEnum {
    NORMAL(0, "普通用户"),
    ADMIN(1, "超级用户"),
    ;

    @Getter
    private int role;
    @Getter
    private String desc;

    RoleEnum(int role, String desc) {
        this.role = role;
        this.desc = desc;
    }

    public static String role(Integer roleId) {
        if (Objects.equals(roleId, 1)) {
            return ADMIN.name();
        } else {
            return NORMAL.name();
        }
    }
}

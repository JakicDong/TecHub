package com.github.jakicdong.techub.api.model.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
/*
* @author JakicDong
* @description 星球来源
* @time 2025/7/10 10:02
*/
@Getter
@AllArgsConstructor
public enum StarSourceEnum {
    /**
     * java进阶
     */
    JAVA_GUIDE(1),
    /**
     * 技术派
     */
    TECH_PAI(2),
    ;
    private int source;
}

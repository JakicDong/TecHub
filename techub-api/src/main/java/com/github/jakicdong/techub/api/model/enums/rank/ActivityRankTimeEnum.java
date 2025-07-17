package com.github.jakicdong.techub.api.model.enums.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
* @author JakicDong
* @description 活跃排行榜时间周期
* @time 2025/7/3 19:23
*/

@Getter
@AllArgsConstructor
public enum ActivityRankTimeEnum {
    DAY(1, "day"),
    MONTH(2, "month"),
    ;

    private int type;
    private String desc;

    public static ActivityRankTimeEnum nameOf(String name) {
        if (DAY.desc.equalsIgnoreCase(name)) {
            return DAY;
        } else if (MONTH.desc.equalsIgnoreCase(name)) {
            return MONTH;
        }
        return null;
    }
}

package com.github.jakicdong.techub.api.model.vo.statistics.dto;

import lombok.Data;


/*
* @author JakicDong
* @description 每天的统计计数dto
* @time 2025/9/20 18:44
*/
@Data
public class StatisticsDayDTO {

    /**
     * 日期
     */
    private String date;

    /**
     * 数量
     */
    private Long pvCount;

    /**
     * UV数量
     */
    private Long uvCount;
}

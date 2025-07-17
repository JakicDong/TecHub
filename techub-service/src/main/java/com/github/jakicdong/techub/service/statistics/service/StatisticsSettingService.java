package com.github.jakicdong.techub.service.statistics.service;


/*
* @author JakicDong
* @description 数据统计后台接口
* @time 2025/7/11 17:04
*/

import com.github.jakicdong.techub.api.model.vo.statistics.dto.StatisticsCountDTO;

public interface StatisticsSettingService {


    /**
     * 保存计数
     *
     * @param host
     */
    void saveRequestCount(String host);
    /**
     * 获取总数
     *
     * @return
     */
    StatisticsCountDTO getStatisticsCount();




}

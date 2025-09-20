package com.github.jakicdong.techub.service.statistics.service;

import com.github.jakicdong.techub.service.statistics.repository.entity.RequestCountDO;

/*
* @author JakicDong
* @description 请求计数服务
* @time 2025/7/12 09:21
*/
public interface RequestCountService {


    Long getPvTotalCount();


    RequestCountDO getRequestCount(String host);

    void insert(String host);

    void incrementCount(Long id);

}

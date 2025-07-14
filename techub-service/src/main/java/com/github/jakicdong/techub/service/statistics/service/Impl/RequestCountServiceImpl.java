package com.github.jakicdong.techub.service.statistics.service.Impl;

import com.github.jakicdong.techub.service.statistics.repository.dao.RequestCountDao;
import com.github.jakicdong.techub.service.statistics.service.RequestCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* @author JakicDong
* @description 请求计数服务
* @time 2025/7/12 09:22
*/
@Slf4j
@Service
public class RequestCountServiceImpl implements RequestCountService {
    @Autowired
    private RequestCountDao requestCountDao;


    @Override
    public Long getPvTotalCount() {
        return requestCountDao.getPvTotalCount();
    }

}

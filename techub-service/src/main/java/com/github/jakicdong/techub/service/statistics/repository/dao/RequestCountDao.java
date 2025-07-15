package com.github.jakicdong.techub.service.statistics.repository.dao;

/*
* @author JakicDong
* @description 请求计数
* @time 2025/7/14 16:19
*/

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.service.statistics.repository.entitu.RequestCountDO;
import com.github.jakicdong.techub.service.statistics.repository.mapper.RequestCountMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public class RequestCountDao extends ServiceImpl<RequestCountMapper, RequestCountDO> {


    public Long getPvTotalCount() {
        return baseMapper.getPvTotalCount();
    }

    public RequestCountDO getRequestCount(String host, Date date){
        return lambdaQuery()
                .eq(RequestCountDO::getHost,host)
                .eq(RequestCountDO::getDate,date)
                .one();
    }

    public void incrementCount(Long id){
        baseMapper.incrementCount(id);
    }

}

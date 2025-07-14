package com.github.jakicdong.techub.service.statistics.repository.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jakicdong.techub.service.statistics.repository.entitu.RequestCountDO;
import org.apache.ibatis.annotations.Select;

/*
* @author JakicDong
* @description 请求计数mapper
* @time 2025/7/14 16:27
*/

public interface RequestCountMapper extends BaseMapper<RequestCountDO> {


    /**
     * 获取 PV 总数
     *
     * @return
     */
    @Select("select sum(cnt) from request_count")
    Long getPvTotalCount();


}

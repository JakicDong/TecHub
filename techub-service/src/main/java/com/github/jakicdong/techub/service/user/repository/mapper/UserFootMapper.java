package com.github.jakicdong.techub.service.user.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserFootStatisticDTO;
import com.github.jakicdong.techub.service.user.repository.entity.UserFootDO;


/*
* @author JakicDong
* @description 用足迹mapper
* @time 2025/7/12 09:56
*/
public interface UserFootMapper extends BaseMapper<UserFootDO> {



    UserFootStatisticDTO getFootCount();
}

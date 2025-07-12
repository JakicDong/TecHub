package com.github.jakicdong.techub.service.user.service;

/*
* @author JakicDong
* @description 用户足迹Service接口
* @time 2025/7/3 20:25
*/

import com.github.jakicdong.techub.api.model.vo.user.dto.UserFootStatisticDTO;

public interface UserFootService {





    //获取用户主页的点赞数、收藏数、留言数、阅读数
    UserFootStatisticDTO getFootCount();

}

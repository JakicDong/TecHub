package com.github.jakicdong.techub.service.statistics.service;

/*
* @author JakicDong
* @description 用户统计服务
* @time 2025/7/4 18:35
*/

public interface UserStatisticService {

    /**
     * 添加在线人数
     *
     * @param add 正数，表示添加在线人数；负数，表示减少在线人数
     * @return
     */
    int incrOnlineUserCnt(int add);

    /**
     * 查询在线用户人数
     *
     * @return
     */
    int getOnlineUserCnt();
}

package com.github.jakicdong.techub.service.rank.service;

/*
* @author JakicDong
* @description 用户活跃度排行相关服务
* @time 2025/7/3 18:45
*/

import com.github.jakicdong.techub.api.model.enums.rank.ActivityRankTimeEnum;
import com.github.jakicdong.techub.api.model.vo.rank.dto.RankItemDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;

import java.util.Collection;
import java.util.List;

public interface UserActivityRankService {

    /**
     * 查询活跃度排行榜
     *
     * @param time
     * @return
     */
    List<RankItemDTO> queryRankList(ActivityRankTimeEnum time, int size);

}

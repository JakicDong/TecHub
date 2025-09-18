package com.github.jakicdong.techub.api.model.vo.rank.dto;

import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;
import lombok.Data;
import lombok.experimental.Accessors;

/*
* @author JakicDong
* @description 排行榜信息
* @time 2025/9/18 16:53
*/
@Data
@Accessors(chain = true)
public class RankItemDTO {

    /**
     * 排名
     */
    private Integer rank;

    /**
     * 评分
     */
    private Integer score;

    /**
     * 用户
     */
    private SimpleUserInfoDTO user;
}

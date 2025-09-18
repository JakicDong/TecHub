package com.github.jakicdong.techub.api.model.vo.rank.dto;

import com.github.jakicdong.techub.api.model.enums.rank.ActivityRankTimeEnum;
import lombok.Data;

import java.util.List;

/*
* @author JakicDong
* @description 排行榜信息
* @time 2025/9/18 16:53
*/
@Data
public class RankInfoDTO {
    private ActivityRankTimeEnum time;
    private List<RankItemDTO> items;
}

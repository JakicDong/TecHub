package com.github.jakicdong.techub.web.front.article.vo;

import com.github.jakicdong.techub.api.model.vo.PageListVo;
import com.github.jakicdong.techub.api.model.vo.article.dto.ColumnDTO;
import com.github.jakicdong.techub.api.model.vo.recommend.SideBarDTO;
import lombok.Data;

import java.util.List;

/*
* @author JakicDong
* @description 专栏VO
* @time 2025/9/3 16:05
*/

@Data
public class ColumnVo {
    /**
     * 专栏列表
     */
    private PageListVo<ColumnDTO> columns;

    /**
     * 侧边栏信息
     */
    private List<SideBarDTO> sideBarItems;

}
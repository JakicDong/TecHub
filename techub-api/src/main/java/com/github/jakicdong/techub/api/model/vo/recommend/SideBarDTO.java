package com.github.jakicdong.techub.api.model.vo.recommend;

import lombok.Data;
import com.github.jakicdong.techub.api.model.enums.SidebarStyleEnum;
import lombok.experimental.Accessors;

import java.util.List;
/*
* @author JakicDong
* @description 侧边推广信息
* @time 2025/7/3 14:42
*/
@Data
@Accessors(chain = true)
public class SideBarDTO {

    private String title;

    private String subTitle;

    private String icon;

    private String img;

    private String url;

    private String content;

    private List<SideBarItemDTO> items;

    /**
     * 侧边栏样式
     *
     * @see SidebarStyleEnum#getStyle()
     */
    private Integer style;
}

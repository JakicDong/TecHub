package com.github.jakicdong.techub.service.sidebar.service;

import com.github.jakicdong.techub.api.model.vo.recommend.SideBarDTO;

import java.util.List;

/*
* @author JakicDong
* @description 侧边栏相关服务
* @time 2025/7/3 18:07
*/
public interface SidebarService {


    /**
     * 查询首页的侧边栏信息
     *
     * @return
     */
    List<SideBarDTO> queryHomeSidebarList();

}

package com.github.jakicdong.techub.web.front.home.vo;

import lombok.Data;

import java.util.List;

/*
* @author JakicDong
* @description 页面显示
* @time 2025/7/1 20:30
*/
@Data
public class IndexVo {
    //todo 添加页面显示的数据
//    /**
//     * 分类列表
//     */
//    private List<CategoryDTO> categories;

    /**
     * 当前选中的分类
     */
    private String currentCategory;

    /**
     * 当前选中的类目id
     */
    private Long categoryId;

//    /**
//     * top 文章列表
//     */
//    private List<ArticleDTO> topArticles;
//
//    /**
//     * 文章列表
//     */
//    private PageListVo<ArticleDTO> articles;
//
//    /**
//     * 登录用户信息
//     */
//    private UserStatisticInfoDTO user;
//
//    /**
//     * 侧边栏信息
//     */
//    private  List<SideBarDTO> sideBarItems;
//
//    /**
//     * 轮播图
//     */
//    private List<CarouseDTO> homeCarouselList;
}

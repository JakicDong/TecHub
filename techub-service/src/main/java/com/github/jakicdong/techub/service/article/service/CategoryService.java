package com.github.jakicdong.techub.service.article.service;

import com.github.jakicdong.techub.api.model.vo.article.dto.CategoryDTO;

import java.util.List;

/*
* @author JakicDong
* @description 文章分类标签service
* @time 2025/7/3 11:07
*/
public interface CategoryService {

    /**
     * 查询类目名
     *
     * @param categoryId
     * @return
     */
    String queryCategoryName(Long categoryId);


    /**
     * 查询所有的分类
     *
     * @return
     */
    List<CategoryDTO> loadAllCategories();

    /**
     * 刷新缓存
     */
    public void refreshCache();

}

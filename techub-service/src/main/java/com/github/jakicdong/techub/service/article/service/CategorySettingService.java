package com.github.jakicdong.techub.service.article.service;

import com.github.jakicdong.techub.api.model.vo.PageVo;
import com.github.jakicdong.techub.api.model.vo.article.CategoryReq;
import com.github.jakicdong.techub.api.model.vo.article.SearchCategoryReq;
import com.github.jakicdong.techub.api.model.vo.article.dto.CategoryDTO;

/*
* @author JakicDong
* @description 分类后台接口
* @time 2025/9/20 19:12
*/
public interface CategorySettingService {

    void saveCategory(CategoryReq categoryReq);

    void deleteCategory(Integer categoryId);

    void operateCategory(Integer categoryId, Integer pushStatus);

    /**
     * 获取category列表
     *
     * @param pageParam
     * @return
     */
    PageVo<CategoryDTO> getCategoryList(SearchCategoryReq params);
}

package com.github.jakicdong.techub.service.article.conveter;

import com.github.jakicdong.techub.api.model.vo.article.dto.CategoryDTO;
import com.github.jakicdong.techub.service.article.repository.entity.CategoryDO;

/*
* @author JakicDong
* @description 文章转换
* @time 2025/7/3 12:58
*/
public class ArticleConverter {

    public static CategoryDTO toDto(CategoryDO category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategory(category.getCategoryName());
        dto.setCategoryId(category.getId());
        dto.setRank(category.getRank());
        dto.setStatus(category.getStatus());
        dto.setSelected(false);
        return dto;
    }

}

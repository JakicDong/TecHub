package com.github.jakicdong.techub.service.article.conveter;

import com.github.jakicdong.techub.api.model.vo.article.CategoryReq;
import com.github.jakicdong.techub.api.model.vo.article.SearchCategoryReq;
import com.github.jakicdong.techub.api.model.vo.article.dto.CategoryDTO;
import com.github.jakicdong.techub.service.article.repository.entity.CategoryDO;
import com.github.jakicdong.techub.service.article.repository.params.SearchCategoryParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/*
* @author JakicDong
* @description 分类结构转换
* @time 2025/9/20 19:08
*/
@Mapper
public interface CategoryStructMapper {
    // instance
    CategoryStructMapper INSTANCE = Mappers.getMapper( CategoryStructMapper.class );

    // req to params
    @Mapping(source = "pageNumber", target = "pageNum")
    SearchCategoryParams toSearchParams(SearchCategoryReq req);

    // do to dto
    @Mapping(source = "id", target = "categoryId")
    @Mapping(source = "categoryName", target = "category")
    CategoryDTO toDTO(CategoryDO categoryDO);

    List<CategoryDTO> toDTOs(List<CategoryDO> list);

    // req to do
    @Mapping(source = "category", target = "categoryName")
    CategoryDO toDO(CategoryReq categoryReq);
}

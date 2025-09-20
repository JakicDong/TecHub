package com.github.jakicdong.techub.service.article.conveter;

import com.github.jakicdong.techub.api.model.vo.article.SearchArticleReq;
import com.github.jakicdong.techub.service.article.repository.params.SearchArticleParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/*
* @author JakicDong
* @description 文章结构转换
* @time 2025/9/20 19:08
*/
@Mapper
public interface ArticleStructMapper {
    ArticleStructMapper INSTANCE = Mappers.getMapper( ArticleStructMapper.class );

    @Mapping(source = "pageNumber", target = "pageNum")
    SearchArticleParams toSearchParams(SearchArticleReq req);
}

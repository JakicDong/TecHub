package com.github.jakicdong.techub.service.article.conveter;

import com.github.jakicdong.techub.api.model.vo.article.SearchTagReq;
import com.github.jakicdong.techub.api.model.vo.article.TagReq;
import com.github.jakicdong.techub.api.model.vo.article.dto.TagDTO;
import com.github.jakicdong.techub.service.article.repository.entity.TagDO;
import com.github.jakicdong.techub.service.article.repository.params.SearchTagParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/*
* @author JakicDong
* @description 标签结构转换
* @time 2025/9/20 19:08
*/
@Mapper
public interface TagStructMapper {
    // instance
    TagStructMapper INSTANCE = Mappers.getMapper( TagStructMapper.class );

    // req to params
    @Mapping(source = "pageNumber", target = "pageNum")
    SearchTagParams toSearchParams(SearchTagReq req);

    // do to dto
    @Mapping(source = "id", target = "tagId")
    @Mapping(source = "tagName", target = "tag")
    TagDTO toDTO(TagDO tagDO);

    List<TagDTO> toDTOs(List<TagDO> list);

    @Mapping(source = "tag", target = "tagName")
    TagDO toDO(TagReq tagReq);
}

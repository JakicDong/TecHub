package com.github.jakicdong.techub.service.user.converter;

import com.github.jakicdong.techub.api.model.vo.user.SearchZsxqUserReq;
import com.github.jakicdong.techub.service.user.repository.params.SearchZsxqWhiteParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/*
* @author JakicDong
* @description 用户相关的结构体转换
* @time 2025/9/20 20:19
*/
@Mapper
public interface UserStructMapper {
    UserStructMapper INSTANCE = Mappers.getMapper( UserStructMapper.class );
    // req to params
    @Mapping(source = "pageNumber", target = "pageNum")
    // state to status
    @Mapping(source = "state", target = "status")
    SearchZsxqWhiteParams toSearchParams(SearchZsxqUserReq req);
}

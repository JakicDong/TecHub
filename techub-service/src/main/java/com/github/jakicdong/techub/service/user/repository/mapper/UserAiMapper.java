package com.github.jakicdong.techub.service.user.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.user.dto.ZsxqUserInfoDTO;
import com.github.jakicdong.techub.service.user.repository.entity.UserAiDO;
import com.github.jakicdong.techub.service.user.repository.params.SearchZsxqWhiteParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* @author JakicDong
* @description ai用户登录mapper接口
* @time 2025/7/4 18:12
*/

public interface UserAiMapper extends BaseMapper<UserAiDO> {

    Long countZsxqUsersByParams(@Param("searchParams") SearchZsxqWhiteParams params);

    List<ZsxqUserInfoDTO> listZsxqUsersByParams(@Param("searchParams") SearchZsxqWhiteParams params,
                                                @Param("pageParam") PageParam newPageInstance);
}

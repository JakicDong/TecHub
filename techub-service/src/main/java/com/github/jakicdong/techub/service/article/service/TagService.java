package com.github.jakicdong.techub.service.article.service;

/*
* @author JakicDong
* @description 标签服务
* @time 2025/9/11 15:02
*/

import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.PageVo;
import com.github.jakicdong.techub.api.model.vo.article.dto.TagDTO;

public interface TagService {

    PageVo<TagDTO> queryTags(String key, PageParam pageParam);

    Long queryTagId(String tag);
}

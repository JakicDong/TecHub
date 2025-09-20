package com.github.jakicdong.techub.service.article.service;

import com.github.jakicdong.techub.api.model.vo.PageVo;
import com.github.jakicdong.techub.api.model.vo.article.SearchTagReq;
import com.github.jakicdong.techub.api.model.vo.article.TagReq;
import com.github.jakicdong.techub.api.model.vo.article.dto.TagDTO;

/*
* @author JakicDong
* @description 标签后台接口
* @time 2025/9/20 19:13
*/
public interface TagSettingService {

    void saveTag(TagReq tagReq);

    void deleteTag(Integer tagId);

    void operateTag(Integer tagId, Integer pushStatus);

    /**
     * 获取tag列表
     *
     * @param pageParam
     * @return
     */
    PageVo<TagDTO> getTagList(SearchTagReq req);

    TagDTO getTagById(Long tagId);
}

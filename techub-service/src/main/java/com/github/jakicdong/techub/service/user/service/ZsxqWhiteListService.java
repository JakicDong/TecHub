package com.github.jakicdong.techub.service.user.service;

import com.github.jakicdong.techub.api.model.enums.user.UserAIStatEnum;
import com.github.jakicdong.techub.api.model.vo.PageVo;
import com.github.jakicdong.techub.api.model.vo.user.SearchZsxqUserReq;
import com.github.jakicdong.techub.api.model.vo.user.ZsxqUserPostReq;
import com.github.jakicdong.techub.api.model.vo.user.dto.ZsxqUserInfoDTO;

import java.util.List;

/*
* @author JakicDong
* @description 微信搜索用户白名单服务接口
* @time 2025/9/20 20:23
*/
public interface ZsxqWhiteListService {
    PageVo<ZsxqUserInfoDTO> getList(SearchZsxqUserReq req);

    void operate(Long id, UserAIStatEnum operate);

    void update(ZsxqUserPostReq req);

    void batchOperate(List<Long> ids, UserAIStatEnum operate);

    void reset(Integer authorId);
}

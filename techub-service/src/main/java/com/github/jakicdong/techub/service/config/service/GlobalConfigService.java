package com.github.jakicdong.techub.service.config.service;

import com.github.jakicdong.techub.api.model.vo.PageVo;
import com.github.jakicdong.techub.api.model.vo.config.GlobalConfigReq;
import com.github.jakicdong.techub.api.model.vo.config.SearchGlobalConfigReq;
import com.github.jakicdong.techub.api.model.vo.config.dto.GlobalConfigDTO;

/*
* @author JakicDong
* @description 全局配置表服务接口
* @time 2025/9/20 19:52
*/
public interface GlobalConfigService {
    PageVo<GlobalConfigDTO> getList(SearchGlobalConfigReq req);

    void save(GlobalConfigReq req);

    void delete(Long id);

    /**
     * 添加敏感词白名单
     *
     * @param word
     */
    void addSensitiveWhiteWord(String word);
}

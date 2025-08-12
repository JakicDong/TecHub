package com.github.jakicdong.techub.service.config.service;

import com.github.jakicdong.techub.api.model.enums.ConfigTypeEnum;
import com.github.jakicdong.techub.api.model.vo.banner.dto.ConfigDTO;

import java.util.List;

/*
* @author JakicDong
* @description Banner前台接口
* @time 2025/7/3 18:06
*/
public interface ConfigService {
    /**
     * 获取 Banner 列表
     *
     * @param configTypeEnum
     * @return
     */
    List<ConfigDTO> getConfigList(ConfigTypeEnum configTypeEnum);



    /**
     * 阅读次数+1
     *
     * @param configId
     * @param extra
     */
    void updateVisit(long configId, String extra);
}

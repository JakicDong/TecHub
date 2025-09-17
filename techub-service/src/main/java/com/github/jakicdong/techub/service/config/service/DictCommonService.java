package com.github.jakicdong.techub.service.config.service;

import java.util.Map;

/*
* @author JakicDong
* @description 字典Service
* @time 2025/9/17 18:29
*/
public interface DictCommonService {

    /**
     * 获取字典值
     * @return
     */
    Map<String, Object> getDict();
}

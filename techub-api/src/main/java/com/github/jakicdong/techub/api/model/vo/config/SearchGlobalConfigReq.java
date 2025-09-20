package com.github.jakicdong.techub.api.model.vo.config;

import lombok.Data;


/*
* @author JakicDong
* @description 全局配置搜索req
* @time 2025/9/20 18:43
*/
@Data
public class SearchGlobalConfigReq {
    // 配置项名称
    private String keywords;
    // 配置项值
    private String value;
    // 备注
    private String comment;
    // 分页
    private Long pageNumber;
    private Long pageSize;
}

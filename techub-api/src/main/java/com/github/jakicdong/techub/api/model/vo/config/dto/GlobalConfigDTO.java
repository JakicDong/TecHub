package com.github.jakicdong.techub.api.model.vo.config.dto;

import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description 全局配置dto
* @time 2025/9/20 18:43
*/
@Data
public class GlobalConfigDTO implements Serializable {
    // uid
    private static final long serialVersionUID = 1L;

    // id
    private Long id;
    // 配置项名称
    private String keywords;
    // 配置项值
    private String value;
    // 备注
    private String comment;
}

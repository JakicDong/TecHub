package com.github.jakicdong.techub.api.model.vo.article.dto;

import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description 通用标签选择
* @time 2025/9/6 15:10
*/

@Data
public class TagSelectDTO implements Serializable {

    /**
     * 类型
     */
    private String selectType;

    /**
     * 描述
     */
    private String selectDesc;

    /**
     * 是否选中
     */
    private Boolean selected;
}

package com.github.jakicdong.techub.api.model.vo.article;

import lombok.Data;

import java.io.Serializable;


/*
* @author JakicDong
* @description 保存Category请求参数
* @time 2025/9/11 15:38
*/
@Data
public class CategoryReq implements Serializable {

    /**
     * ID
     */
    private Long categoryId;

    /**
     * 类目名称
     */
    private String category;

    /**
     * 排序
     */
    private Integer rank;
}

package com.github.jakicdong.techub.api.model.vo.article;

import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description 保存Tag的请求参数
* @time 2025/9/11 15:40
*/

@Data
public class TagReq implements Serializable {

    /**
     * ID
     */
    private Long tagId;

    /**
     * 标签名称
     */
    private String tag;

    /**
     * 类目ID
     */
    private Long categoryId;
}

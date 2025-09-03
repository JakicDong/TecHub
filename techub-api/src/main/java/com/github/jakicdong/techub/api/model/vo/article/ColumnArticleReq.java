package com.github.jakicdong.techub.api.model.vo.article;

import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description 保存专栏文章请求参数
* @time 2025/9/3 16:17
*/
@Data
public class ColumnArticleReq implements Serializable {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 专栏ID
     */
    private Long columnId;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 文章排序
     */
    private Integer sort;

    /**
     * 教程标题
     */
    private String shortTitle;

    /**
     * 阅读方式
     */
    private Integer read;
}

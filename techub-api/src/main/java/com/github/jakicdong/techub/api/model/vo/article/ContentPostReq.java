package com.github.jakicdong.techub.api.model.vo.article;

import lombok.Data;

import java.io.Serializable;


/*
* @author JakicDong
* @description 发布文章请求参数
* @time 2025/9/11 15:38
*/
@Data
public class ContentPostReq implements Serializable {
    /**
     * 正文内容
     */
    private String content;
}
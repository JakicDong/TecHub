package com.github.jakicdong.techub.api.model.vo.article.dto;

/*
* @author JakicDong
* @description 创作历程
* @time 2025/7/3 14:40
*/

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class YearArticleDTO {

    /**
     * 年份
     */
    private String year;

    /**
     * 文章数量
     */
    private Integer articleCount;
}

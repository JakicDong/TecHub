package com.github.jakicdong.techub.service.article.repository.params;

import lombok.Data;

/*
* @author JakicDong
* @description
* @time 2025/9/11 15:36
*/
@Data
public class ColumnArticleParams {
    // 教程 ID
    private Long columnId;
    // 文章 ID
    private Long articleId;
    // section
    private Integer section;
}

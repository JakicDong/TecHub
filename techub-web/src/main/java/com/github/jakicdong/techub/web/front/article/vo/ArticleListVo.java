package com.github.jakicdong.techub.web.front.article.vo;

import com.github.jakicdong.techub.api.model.vo.PageListVo;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleDTO;
import lombok.Data;

/*
* @author JakicDong
* @description 文章列表视图
* @time 2025/9/11 15:00
*/

@Data
public class ArticleListVo {
    /**
     * 归档类型
     */
    private String archives;
    /**
     * 归档id
     */
    private Long archiveId;

    private PageListVo<ArticleDTO> articles;
}


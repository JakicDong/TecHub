package com.github.jakicdong.techub.service.article.service;

import com.github.jakicdong.techub.api.model.vo.PageListVo;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleDTO;

/*
* @author JakicDong
* @description 文章关联推荐
* @time 2025/9/11 17:20
*/
public interface ArticleRecommendService {

    /**
     * 文章关联推荐
     *
     * @param article
     * @param pageParam
     * @return
     */
    PageListVo<ArticleDTO> relatedRecommend(Long article, PageParam pageParam);
}

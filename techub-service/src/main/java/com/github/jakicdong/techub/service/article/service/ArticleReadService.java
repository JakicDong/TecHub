package com.github.jakicdong.techub.service.article.service;


import com.github.jakicdong.techub.api.model.vo.PageListVo;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.SimpleArticleDTO;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDO;

import java.util.List;
import java.util.Map;

/*
* @author JakicDong
* @description 文章的读服务
* @time 2025/7/3 18:07
*/
public interface ArticleReadService {
    /**
     * 查询热门文章
     *
     * @param pageParam
     * @return
     */
    PageListVo<SimpleArticleDTO> queryHotArticlesForRecommend(PageParam pageParam);

    /**
     * 查询某个分类下的文章，支持翻页
     *
     * @param categoryId
     * @param page
     * @return
     */
    PageListVo<ArticleDTO> queryArticlesByCategory(Long categoryId, PageParam page);

    /**
     * 文章实体补齐统计、作者、分类标签等信息
     *
     * @param records
     * @param pageSize
     * @return
     */
    PageListVo<ArticleDTO> buildArticleListVo(List<ArticleDO> records, long pageSize);

    /**
     * 获取 Top 文章
     *
     * @param categoryId
     * @return
     */
    List<ArticleDTO> queryTopArticlesByCategory(Long categoryId);


    /**
     * 根据分类统计文章计数
     *
     * @return
     */
    Map<Long, Long> queryArticleCountsByCategory();

    /**
     * 返回总的文章计数
     *
     * @return
     */
    Long getArticleCount();


}

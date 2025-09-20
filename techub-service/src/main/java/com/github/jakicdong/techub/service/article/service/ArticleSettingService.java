package com.github.jakicdong.techub.service.article.service;

import com.github.jakicdong.techub.api.model.enums.OperateArticleEnum;
import com.github.jakicdong.techub.api.model.vo.PageVo;
import com.github.jakicdong.techub.api.model.vo.article.ArticlePostReq;
import com.github.jakicdong.techub.api.model.vo.article.SearchArticleReq;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleAdminDTO;

/*
* @author JakicDong
* @description 文章后台接口
* @time 2025/9/20 19:12
*/
public interface ArticleSettingService {

    /**
     * 更新文章
     *
     * @param req
     */
    void updateArticle(ArticlePostReq req);

    /**
     * 获取文章列表
     *
     * @param req
     * @return
     */
    PageVo<ArticleAdminDTO> getArticleList(SearchArticleReq req);

    /**
     * 删除文章
     *
     * @param articleId
     */
    void deleteArticle(Long articleId);

    /**
     * 操作文章
     *
     * @param articleId
     * @param operate
     */
    void operateArticle(Long articleId, OperateArticleEnum operate);
}

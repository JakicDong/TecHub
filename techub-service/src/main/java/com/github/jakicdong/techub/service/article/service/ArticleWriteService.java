package com.github.jakicdong.techub.service.article.service;

import com.github.jakicdong.techub.api.model.vo.article.ArticlePostReq;


/*
* @author JakicDong
* @description 文章写入服务
* @time 2025/9/11 16:16
*/
public interface ArticleWriteService {

    /**
     * 保存or更新文章
     *
     * @param req    上传的文章体
     * @param author 作者
     * @return 返回文章主键
     */
    Long saveArticle(ArticlePostReq req, Long author);

    /**
     * 删除文章
     *
     * @param articleId   文章id
     * @param loginUserId 执行操作的用户
     */
    void deleteArticle(Long articleId, Long loginUserId);


}

package com.github.jakicdong.techub.service.article.service;

/*
* @author JakicDong
* @description 专栏服务
* @time 2025/7/12 09:24
*/

import com.github.jakicdong.techub.service.article.repository.entity.ColumnArticleDO;

public interface ColumnService {

    /**
     * 返回教程数量
     *
     * @return
     */
    Long getTutorialCount();

    /**
     * 根据文章id，构建对应的专栏详情地址
     *
     * @param articleId 文章主键
     * @return 专栏详情页
     */
    ColumnArticleDO getColumnArticleRelation(Long articleId);

}

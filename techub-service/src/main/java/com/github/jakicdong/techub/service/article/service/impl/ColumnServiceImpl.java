package com.github.jakicdong.techub.service.article.service.impl;

import com.github.jakicdong.techub.service.article.repository.dao.ColumnArticleDao;
import com.github.jakicdong.techub.service.article.repository.dao.ColumnDao;
import com.github.jakicdong.techub.service.article.repository.entity.ColumnArticleDO;
import com.github.jakicdong.techub.service.article.service.ColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ColumnServiceImpl implements ColumnService {
    @Autowired
    private ColumnDao columnDao;

    @Autowired
    private ColumnArticleDao columnArticleDao;


    @Override
    public Long getTutorialCount() {
        return this.columnDao.countColumnArticles();
    }

    @Override
    public ColumnArticleDO getColumnArticleRelation(Long articleId) {
        return columnArticleDao.selectColumnArticleByArticleId(articleId);
    }
}

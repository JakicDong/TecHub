package com.github.jakicdong.techub.service.article.service.impl;

import com.github.jakicdong.techub.service.article.repository.dao.ColumnDao;
import com.github.jakicdong.techub.service.article.service.ColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ColumnServiceImpl implements ColumnService {
    @Autowired
    private ColumnDao columnDao;


    @Override
    public Long getTutorialCount() {
        return this.columnDao.countColumnArticles();
    }
}

package com.github.jakicdong.techub.service.article.service.impl;

import com.github.jakicdong.techub.api.model.enums.pay.PayStatusEnum;
import com.github.jakicdong.techub.service.article.repository.dao.ArticlePayDao;
import com.github.jakicdong.techub.service.article.repository.entity.ArticlePayRecordDO;
import com.github.jakicdong.techub.service.article.service.ArticlePayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticlePayServiceImpl implements ArticlePayService {

    @Autowired
    private ArticlePayDao articlePayDao;

    @Override
    public boolean hasPayed(Long article, Long currentUerId) {
        ArticlePayRecordDO dbRecord = articlePayDao.queryRecordByArticleId(article, currentUerId);
        if (dbRecord == null) {
            return false;
        }

        return PayStatusEnum.SUCCEED.getStatus().equals(dbRecord.getPayStatus());
    }
}

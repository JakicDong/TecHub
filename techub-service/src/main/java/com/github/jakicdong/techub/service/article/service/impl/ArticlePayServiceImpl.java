package com.github.jakicdong.techub.service.article.service.impl;

import com.github.jakicdong.techub.api.model.enums.pay.PayStatusEnum;
import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;
import com.github.jakicdong.techub.service.article.repository.dao.ArticlePayDao;
import com.github.jakicdong.techub.service.article.repository.entity.ArticlePayRecordDO;
import com.github.jakicdong.techub.service.article.service.ArticlePayService;
import com.github.jakicdong.techub.service.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ArticlePayServiceImpl implements ArticlePayService {

    @Autowired
    private ArticlePayDao articlePayDao;

    @Autowired
    private UserService userService;

    @Override
    public boolean hasPayed(Long article, Long currentUerId) {
        ArticlePayRecordDO dbRecord = articlePayDao.queryRecordByArticleId(article, currentUerId);
        if (dbRecord == null) {
            return false;
        }

        return PayStatusEnum.SUCCEED.getStatus().equals(dbRecord.getPayStatus());
    }

    /**
     * 查询文章的打上用户列表
     *
     * @param articleId
     * @return
     */
    public List<SimpleUserInfoDTO> queryPayUsers(Long articleId) {
        List<Long> users = articlePayDao.querySucceedPayUsersByArticleId(articleId);
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }

        return userService.batchQuerySimpleUserInfo(users);
    }
}

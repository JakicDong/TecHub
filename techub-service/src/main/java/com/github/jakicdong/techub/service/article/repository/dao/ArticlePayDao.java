package com.github.jakicdong.techub.service.article.repository.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jakicdong.techub.api.model.enums.pay.PayStatusEnum;
import com.github.jakicdong.techub.service.article.repository.entity.ArticlePayRecordDO;
import com.github.jakicdong.techub.service.article.repository.mapper.ArticlePayRecordMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/*
* @author JakicDong
* @description 文章支付记录
* @time 2025/8/11 17:35
*/

@Repository
public class ArticlePayDao extends ServiceImpl<ArticlePayRecordMapper, ArticlePayRecordDO> {

    /**
     * 用户的文章支付记录
     *
     * @param articleId 文章id
     * @param payUserId 支付用户id
     * @return 支付记录
     */
    public ArticlePayRecordDO queryRecordByArticleId(Long articleId, Long payUserId) {
        List<ArticlePayRecordDO> list = lambdaQuery()
                .eq(ArticlePayRecordDO::getArticleId, articleId)
                .eq(ArticlePayRecordDO::getPayUserId, payUserId).list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 查询文章成功支付的用户id
     *
     * @param articleId 文章id
     * @return
     */
    public List<Long> querySucceedPayUsersByArticleId(Long articleId) {
        List<ArticlePayRecordDO> records = lambdaQuery().select(ArticlePayRecordDO::getPayUserId)
                .eq(ArticlePayRecordDO::getArticleId, articleId)
                .eq(ArticlePayRecordDO::getPayStatus, PayStatusEnum.SUCCEED.getStatus())
                .list();
        return records.stream().map(ArticlePayRecordDO::getPayUserId).collect(Collectors.toList());
    }

}

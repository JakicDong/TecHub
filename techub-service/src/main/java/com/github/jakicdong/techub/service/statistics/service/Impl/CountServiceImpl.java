package com.github.jakicdong.techub.service.statistics.service.Impl;

import com.github.jakicdong.techub.api.model.vo.user.dto.ArticleFootCountDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserStatisticInfoDTO;
import com.github.jakicdong.techub.service.article.repository.dao.ArticleDao;
import com.github.jakicdong.techub.service.statistics.constants.CountConstants;
import com.github.jakicdong.techub.service.statistics.service.CountService;
import com.github.jakicdong.techub.service.user.repository.dao.UserFootDao;
import org.springframework.stereotype.Service;
import com.github.jakicdong.techub.core.cache.RedisClient;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CountServiceImpl implements CountService {

    private final UserFootDao userFootDao;
    public CountServiceImpl (UserFootDao userFootDao) {
        this.userFootDao = userFootDao;
    }
    @Resource
    private ArticleDao articleDao;






    @Override
    public ArticleFootCountDTO queryArticleStatisticInfo(Long articleId) {
        Map<String, Integer> ans = RedisClient.hGetAll(CountConstants.ARTICLE_STATISTIC_INFO + articleId, Integer.class);
        ArticleFootCountDTO info = new ArticleFootCountDTO();
        info.setPraiseCount(ans.getOrDefault(CountConstants.PRAISE_COUNT, 0));
        info.setCollectionCount(ans.getOrDefault(CountConstants.COLLECTION_COUNT, 0));
        info.setCommentCount(ans.getOrDefault(CountConstants.COMMENT_COUNT, 0));
        info.setReadCount(ans.getOrDefault(CountConstants.READ_COUNT, 0));
        return info;
    }

    @Override
    public UserStatisticInfoDTO queryUserStatisticInfo(Long userId) {
        Map<String, Integer> ans = RedisClient.hGetAll(CountConstants.USER_STATISTIC_INFO + userId, Integer.class);
        UserStatisticInfoDTO info = new UserStatisticInfoDTO();
        info.setFollowCount(ans.getOrDefault(CountConstants.FOLLOW_COUNT, 0));
        info.setArticleCount(ans.getOrDefault(CountConstants.ARTICLE_COUNT, 0));
        info.setPraiseCount(ans.getOrDefault(CountConstants.PRAISE_COUNT, 0));
        info.setCollectionCount(ans.getOrDefault(CountConstants.COLLECTION_COUNT, 0));
        info.setReadCount(ans.getOrDefault(CountConstants.READ_COUNT, 0));
        info.setFansCount(ans.getOrDefault(CountConstants.FANS_COUNT, 0));
        return info;
    }


    @Override
    public void incrArticleReadCount(Long authorUserId, Long articleId) {
        // db层的计数+1
        articleDao.incrReadCount(articleId);
        // redis计数器 +1
        RedisClient.pipelineAction()
                .add(CountConstants.ARTICLE_STATISTIC_INFO + articleId, CountConstants.READ_COUNT,
                        (connection, key, value) -> connection.hIncrBy(key, value, 1))
                .add(CountConstants.USER_STATISTIC_INFO + authorUserId, CountConstants.READ_COUNT,
                        (connection, key, value) -> connection.hIncrBy(key, value, 1))
                .execute();
    }

    /**
     * 查询评论的点赞数
     *
     * @param commentId
     * @return
     */
    @Override
    public Long queryCommentPraiseCount(Long commentId) {
        return userFootDao.countCommentPraise(commentId);
    }

}

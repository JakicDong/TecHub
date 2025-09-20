package com.github.jakicdong.techub.service.rank.service.listener;

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.enums.ArticleEventEnum;
import com.github.jakicdong.techub.api.model.event.ArticleMsgEvent;
import com.github.jakicdong.techub.api.model.vo.notify.NotifyMsgEvent;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDO;
import com.github.jakicdong.techub.service.comment.repository.entity.CommentDO;
import com.github.jakicdong.techub.service.rank.service.UserActivityRankService;
import com.github.jakicdong.techub.service.rank.service.model.ActivityScoreBo;
import com.github.jakicdong.techub.service.user.repository.entity.UserFootDO;
import com.github.jakicdong.techub.service.user.repository.entity.UserRelationDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/*
* @author JakicDong
* @description 用户活跃度相关的消息监听器
* @time 2025/9/20 20:07
*/

@Component
public class UserActivityListener {
    @Autowired
    private UserActivityRankService userActivityRankService;

    /**
     * 用户操作行为，增加对应的积分
     *
     * @param msgEvent
     */
    @EventListener(classes = NotifyMsgEvent.class)
    @Async
    public void notifyMsgListener(NotifyMsgEvent msgEvent) {
        switch (msgEvent.getNotifyType()) {
            case COMMENT:
            case REPLY:
                CommentDO comment = (CommentDO) msgEvent.getContent();
                userActivityRankService.addActivityScore(ReqInfoContext.getReqInfo().getUserId(), new ActivityScoreBo().setRate(true).setArticleId(comment.getArticleId()));
                break;
            case COLLECT:
                UserFootDO foot = (UserFootDO) msgEvent.getContent();
                userActivityRankService.addActivityScore(ReqInfoContext.getReqInfo().getUserId(), new ActivityScoreBo().setCollect(true).setArticleId(foot.getDocumentId()));
                break;
            case CANCEL_COLLECT:
                foot = (UserFootDO) msgEvent.getContent();
                userActivityRankService.addActivityScore(ReqInfoContext.getReqInfo().getUserId(), new ActivityScoreBo().setCollect(false).setArticleId(foot.getDocumentId()));
                break;
            case PRAISE:
                foot = (UserFootDO) msgEvent.getContent();
                userActivityRankService.addActivityScore(ReqInfoContext.getReqInfo().getUserId(), new ActivityScoreBo().setPraise(true).setArticleId(foot.getDocumentId()));
                break;
            case CANCEL_PRAISE:
                foot = (UserFootDO) msgEvent.getContent();
                userActivityRankService.addActivityScore(ReqInfoContext.getReqInfo().getUserId(), new ActivityScoreBo().setPraise(false).setArticleId(foot.getDocumentId()));
                break;
            case FOLLOW:
                UserRelationDO relation = (UserRelationDO) msgEvent.getContent();
                userActivityRankService.addActivityScore(ReqInfoContext.getReqInfo().getUserId(), new ActivityScoreBo().setFollow(true).setFollowedUserId(relation.getUserId()));
                break;
            case CANCEL_FOLLOW:
                relation = (UserRelationDO) msgEvent.getContent();
                userActivityRankService.addActivityScore(ReqInfoContext.getReqInfo().getUserId(), new ActivityScoreBo().setFollow(false).setFollowedUserId(relation.getUserId()));
                break;
            default:
        }
    }

    /**
     * 发布文章，更新对应的积分
     *
     * @param event
     */
    @Async
    @EventListener(ArticleMsgEvent.class)
    public void publishArticleListener(ArticleMsgEvent<ArticleDO> event) {
        ArticleEventEnum type = event.getType();
        if (type == ArticleEventEnum.ONLINE) {
            userActivityRankService.addActivityScore(ReqInfoContext.getReqInfo().getUserId(), new ActivityScoreBo().setPublishArticle(true).setArticleId(event.getContent().getId()));
        }
    }

}

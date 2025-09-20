package com.github.jakicdong.techub.service.comment.service.impl;

import com.github.jakicdong.techub.api.model.enums.NotifyTypeEnum;
import com.github.jakicdong.techub.api.model.enums.YesOrNoEnum;
import com.github.jakicdong.techub.api.model.exception.ExceptionUtil;
import com.github.jakicdong.techub.api.model.vo.comment.CommentSaveReq;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import com.github.jakicdong.techub.api.model.vo.notify.NotifyMsgEvent;
import com.github.jakicdong.techub.core.util.NumUtil;
import com.github.jakicdong.techub.core.util.SpringUtil;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDO;
import com.github.jakicdong.techub.service.article.service.ArticleReadService;
import com.github.jakicdong.techub.service.comment.converter.CommentConverter;
import com.github.jakicdong.techub.service.comment.repository.dao.CommentDao;
import com.github.jakicdong.techub.service.comment.repository.entity.CommentDO;
import com.github.jakicdong.techub.service.comment.service.CommentWriteService;
import com.github.jakicdong.techub.service.user.service.userfoot.UserFootService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class CommentWriteServiceImpl implements CommentWriteService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ArticleReadService articleReadService;
    @Autowired
    private UserFootService userFootService;

    /*
    * @author JakicDong
    * @description 保存或更新评论服务,事务回滚
    * @time 2025/9/3 11:49
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveComment(CommentSaveReq commentSaveReq) {
        // 保存评论
        CommentDO comment;
        if (NumUtil.nullOrZero(commentSaveReq.getCommentId())) {
            comment = addComment(commentSaveReq);
        } else {
            comment = updateComment(commentSaveReq);
        }
        return comment.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long commentId, Long userId) {
        CommentDO commentDO = commentDao.getById(commentId);//先获取这个评论
        // 1.校验评论，是否越权，文章是否存在
        if(commentDO == null){
            throw ExceptionUtil.of(StatusEnum.COMMENT_NOT_EXISTS, "评论ID=" + commentId);
        }
        if (!Objects.equals(commentDO.getUserId(), userId)) {
            throw ExceptionUtil.of(StatusEnum.FORBID_ERROR_MIXED, "无权删除评论");
        }
        // 获取文章信息
        ArticleDO article = articleReadService.queryBasicArticle(commentDO.getArticleId());
        if (article == null) {
            throw ExceptionUtil.of(StatusEnum.ARTICLE_NOT_EXISTS, commentDO.getArticleId());
        }

        // 2.删除评论、足迹
        commentDO.setDeleted(YesOrNoEnum.YES.getCode());
        commentDao.updateById(commentDO);
        CommentDO parentComment = getParentCommentUser(commentDO.getParentCommentId());
        userFootService.removeCommentFoot(commentDO, article.getUserId(), parentComment == null ? null : parentComment.getUserId());

        // 3. 发布删除评论事件
        SpringUtil.publishEvent(new NotifyMsgEvent<>(this, NotifyTypeEnum.DELETE_COMMENT, commentDO));
        if (NumUtil.upZero(commentDO.getParentCommentId())) {
            // 评论
            SpringUtil.publishEvent(new NotifyMsgEvent<>(this, NotifyTypeEnum.DELETE_REPLY, commentDO));
        }
    }

    /*
    * @author JakicDong
    * @description 添加评论
    * @time 2025/9/3 11:59
    */
    private CommentDO addComment(CommentSaveReq commentSaveReq){
        //我的
        // 0.获取父评论信息，校验是否存在
        CommentDO parentComment = getParentCommentUser(commentSaveReq.getParentCommentId());
        Long parentUser = parentComment == null ? null : parentComment.getUserId();

        // 1. 保存评论内容
        CommentDO commentDO = CommentConverter.toDo(commentSaveReq);
        Date now = new Date();
        commentDO.setCreateTime(now);
        commentDO.setUpdateTime(now);
        commentDao.save(commentDO);

        // 2. 保存足迹信息 : 文章的已评信息 + 评论的已评信息
        ArticleDO articleDO = articleReadService.queryBasicArticle(commentSaveReq.getArticleId());
        if(articleDO == null){
            throw ExceptionUtil.of(StatusEnum.ARTICLE_NOT_EXISTS,commentSaveReq.getArticleId());
        }
        userFootService.saveCommentFoot(commentDO, articleDO.getUserId(), parentUser);

        // 3. 触发杠精机器人
        //todo 做一个基于大模型的自动回复机器人

        // 4. 发布添加/回复评论事件
        SpringUtil.publishEvent(new NotifyMsgEvent<>(this, NotifyTypeEnum.COMMENT,commentDO));
        if(NumUtil.nullOrZero(parentUser)){
            //评论回复事件
            SpringUtil.publishEvent(new NotifyMsgEvent<>(this, NotifyTypeEnum.REPLY,commentDO));
        }
        return commentDO;
    }
    /*
    * @author JakicDong
    * @description 更新评论
    * @time 2025/9/3 11:59
    */
    private CommentDO updateComment(CommentSaveReq commentSaveReq){
        //更新评论
        CommentDO commentDO = commentDao.getById(commentSaveReq.getCommentId());
        if(commentDO == null){
            throw ExceptionUtil.of(StatusEnum.COMMENT_NOT_EXISTS,commentSaveReq.getCommentId());
        }
        commentDO.setContent(commentSaveReq.getCommentContent());
        commentDO.setUpdateTime(new Date());
        commentDao.updateById(commentDO);
        return commentDO;
    }

    /*
    * @author JakicDong
    * @description 获取父评论信息
    * @time 2025/9/3 12:03
    */
    private CommentDO getParentCommentUser(Long parentCommentId){
        //判断id是否有效
        if(NumUtil.nullOrZero(parentCommentId)){
            return null;
        }
        CommentDO parent = commentDao.getById(parentCommentId);
        if(parent == null){
            throw ExceptionUtil.of(StatusEnum.COMMENT_NOT_EXISTS,"父评论="+parentCommentId);
        }
        return parent;
    }



}

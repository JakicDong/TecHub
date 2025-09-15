package com.github.jakicdong.techub.service.article.repository.listener;

import com.alibaba.fastjson.JSONObject;
import com.github.jakicdong.techub.api.model.constant.KafkaTopicConstant;
import com.github.jakicdong.techub.api.model.enums.DocumentTypeEnum;
import com.github.jakicdong.techub.api.model.enums.NotifyStatEnum;
import com.github.jakicdong.techub.api.model.enums.NotifyTypeEnum;
import com.github.jakicdong.techub.api.model.enums.OperateTypeEnum;
import com.github.jakicdong.techub.api.model.vo.kafka.ArticleKafkaMessageDTO;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDO;
import com.github.jakicdong.techub.service.article.service.ArticleReadService;
import com.github.jakicdong.techub.service.constant.RedisConstant;
import com.github.jakicdong.techub.service.notify.repository.entity.NotifyMsgDO;
import com.github.jakicdong.techub.service.notify.service.NotifyService;
import com.github.jakicdong.techub.service.user.repository.dao.UserFootDao;
import com.github.jakicdong.techub.service.user.repository.entity.UserFootDO;
import com.github.jakicdong.techub.service.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ArticleKafkaListener {

    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private NotifyService notifyService;
    
    @Autowired
    private ArticleReadService articleReadService;
    
    @Autowired
    private UserFootDao userFootDao;

    @KafkaListener(topics = {KafkaTopicConstant.ARTICLE_TOPIC})
    public void consumer(ConsumerRecord<?, ?> consumerRecord) {
        log.info("监听中、、、");
        
        try {
            Optional<?> value = Optional.ofNullable(consumerRecord.value());

            if (value.isPresent()) {
                String msg = value.get().toString();
                log.info("收到Kafka消息: {}", msg);
                
                ArticleKafkaMessageDTO articleKafkaMessageDTO = JSONObject.parseObject(msg, ArticleKafkaMessageDTO.class);
                int type = articleKafkaMessageDTO.getType();
                Long userId = articleKafkaMessageDTO.getTargetUserId();
                String sourceUserName = articleKafkaMessageDTO.getSourceUserName();
                String articleTitle = articleKafkaMessageDTO.getArticleTitle();

                log.info("解析消息 - 用户: {}, 目标用户: {}, 文章: {}, 操作类型: {}", 
                        sourceUserName, userId, articleTitle, type);

                // 2-点赞、4-取消点赞；3-收藏、5-取消收藏；6-评论；8-删除评论
                if (type == 2) {
                    // 点赞
                    handlePraise(articleKafkaMessageDTO);
                    
                } else if (type == 4) {
                    // 取消点赞
                    handleCancelPraise(articleKafkaMessageDTO);
                    
                } else if (type == 3) {
                    // 收藏
                    handleCollection(articleKafkaMessageDTO);
                    
                } else if (type == 5) {
                    // 取消收藏
                    handleCancelCollection(articleKafkaMessageDTO);
                    
                } else if (type == 6) {
                    // 评论
                    handleComment(articleKafkaMessageDTO);
                    
                } else if (type == 8) {
                    // 删除评论
                    handleDeleteComment(articleKafkaMessageDTO);
                    
                } else {
                    log.warn("未知的操作类型: {}", type);
                }
                
                log.info("消息处理完成");
            } else {
                log.warn("收到空消息");
            }
        } catch (Exception e) {
            log.error("处理Kafka消息失败", e);
        }
    }

    /**
     * 处理点赞操作
     */
    private void handlePraise(ArticleKafkaMessageDTO message) {
        Long targetUserId = message.getTargetUserId();
        String sourceUserName = message.getSourceUserName();
        String articleTitle = message.getArticleTitle();
        
        log.info("开始处理点赞操作 - 用户: {} 点赞了用户: {} 的文章: {}", 
                sourceUserName, targetUserId, articleTitle);
        
        // 1. Redis计数
        String praiseKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.PRAISE + targetUserId;
        String totalKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.TOTAL + targetUserId;
        
        long praiseCount = redisUtil.incr(praiseKey, 1);
        long totalCount = redisUtil.incr(totalKey, 1);
        
        log.info("Redis计数完成 - 用户: {}, 点赞数: {}, 总通知数: {}", targetUserId, praiseCount, totalCount);
        
        // 2. 保存通知消息到数据库
        saveNotifyMessage(message, NotifyTypeEnum.PRAISE);
        
        // 3. 发送WebSocket实时通知
        String notifyMsg = String.format("太棒了，%s 点赞了您的文章《%s》!!!", sourceUserName, articleTitle);
        notifyService.notifyToUser(targetUserId, notifyMsg);
        
        log.info("点赞处理完成 - 用户: {}", targetUserId);
    }
    
    /**
     * 处理取消点赞操作
     */
    private void handleCancelPraise(ArticleKafkaMessageDTO message) {
        Long targetUserId = message.getTargetUserId();
        String sourceUserName = message.getSourceUserName();
        
        log.info("开始处理取消点赞操作 - 用户: {} 取消点赞了用户: {}", sourceUserName, targetUserId);
        
        // 1. Redis计数
        String praiseKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.PRAISE + targetUserId;
        String totalKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.TOTAL + targetUserId;
        
        long praiseCount = redisUtil.decr(praiseKey, 1);
        long totalCount = redisUtil.decr(totalKey, 1);
        
        log.info("Redis计数完成 - 用户: {}, 点赞数: {}, 总通知数: {}", targetUserId, praiseCount, totalCount);
        
        log.info("取消点赞处理完成 - 用户: {}", targetUserId);
    }
    
    /**
     * 处理收藏操作
     */
    private void handleCollection(ArticleKafkaMessageDTO message) {
        Long targetUserId = message.getTargetUserId();
        String sourceUserName = message.getSourceUserName();
        String articleTitle = message.getArticleTitle();
        
        log.info("开始处理收藏操作 - 用户: {} 收藏了用户: {} 的文章: {}", 
                sourceUserName, targetUserId, articleTitle);
        
        // 1. Redis计数
        String collectionKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.COLLECTION + targetUserId;
        String totalKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.TOTAL + targetUserId;
        
        long collectionCount = redisUtil.incr(collectionKey, 1);
        long totalCount = redisUtil.incr(totalKey, 1);
        
        log.info("Redis计数完成 - 用户: {}, 收藏数: {}, 总通知数: {}", targetUserId, collectionCount, totalCount);
        
        // 2. 保存通知消息到数据库
        saveNotifyMessage(message, NotifyTypeEnum.COLLECT);
        
        // 3. 发送WebSocket实时通知
        String notifyMsg = String.format("太棒了，%s 收藏了您的文章《%s》!!!", sourceUserName, articleTitle);
        notifyService.notifyToUser(targetUserId, notifyMsg);
        
        log.info("收藏处理完成 - 用户: {}", targetUserId);
    }
    
    /**
     * 处理取消收藏操作
     */
    private void handleCancelCollection(ArticleKafkaMessageDTO message) {
        Long targetUserId = message.getTargetUserId();
        String sourceUserName = message.getSourceUserName();
        
        log.info("开始处理取消收藏操作 - 用户: {} 取消收藏了用户: {}", sourceUserName, targetUserId);
        
        // 1. Redis计数
        String collectionKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.COLLECTION + targetUserId;
        String totalKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.TOTAL + targetUserId;
        
        long collectionCount = redisUtil.decr(collectionKey, 1);
        long totalCount = redisUtil.decr(totalKey, 1);
        
        log.info("Redis计数完成 - 用户: {}, 收藏数: {}, 总通知数: {}", targetUserId, collectionCount, totalCount);
        
        log.info("取消收藏处理完成 - 用户: {}", targetUserId);
    }
    
    /**
     * 处理评论操作
     */
    private void handleComment(ArticleKafkaMessageDTO message) {
        Long targetUserId = message.getTargetUserId();
        String sourceUserName = message.getSourceUserName();
        String articleTitle = message.getArticleTitle();
        
        log.info("开始处理评论操作 - 用户: {} 评论了用户: {} 的文章: {}", 
                sourceUserName, targetUserId, articleTitle);
        
        // 1. Redis计数
        String commentKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.COMMENT + targetUserId;
        String totalKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.TOTAL + targetUserId;
        
        long commentCount = redisUtil.incr(commentKey, 1);
        long totalCount = redisUtil.incr(totalKey, 1);
        
        log.info("Redis计数完成 - 用户: {}, 评论数: {}, 总通知数: {}", targetUserId, commentCount, totalCount);
        
        // 2. 保存通知消息到数据库
        saveNotifyMessage(message, NotifyTypeEnum.COMMENT);
        
        // 3. 发送WebSocket实时通知
        String notifyMsg = String.format("太棒了，%s 评论了您的文章《%s》!!!", sourceUserName, articleTitle);
        notifyService.notifyToUser(targetUserId, notifyMsg);
        
        log.info("评论处理完成 - 用户: {}", targetUserId);
    }
    
    /**
     * 处理删除评论操作
     */
    private void handleDeleteComment(ArticleKafkaMessageDTO message) {
        Long targetUserId = message.getTargetUserId();
        String sourceUserName = message.getSourceUserName();
        
        log.info("开始处理删除评论操作 - 用户: {} 删除了对用户: {} 的评论", sourceUserName, targetUserId);
        
        // 1. Redis计数
        String recoverKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.RECOVER + targetUserId;
        String totalKey = RedisConstant.REDIS_PAI + RedisConstant.REDIS_PRE_ARTICLE
                + RedisConstant.TOTAL + targetUserId;
        
        long recoverCount = redisUtil.incr(recoverKey, 1);
        long totalCount = redisUtil.incr(totalKey, 1);
        
        log.info("Redis计数完成 - 用户: {}, 删除数: {}, 总通知数: {}", targetUserId, recoverCount, totalCount);
        
        log.info("删除评论处理完成 - 用户: {}", targetUserId);
    }
    
    /**
     * 保存通知消息到数据库
     */
    private void saveNotifyMessage(ArticleKafkaMessageDTO message, NotifyTypeEnum notifyType) {
        try {
            // 查找用户足迹记录
            UserFootDO userFoot = findUserFoot(message);
            if (userFoot != null) {
                // 使用原有的通知服务保存消息
                notifyService.saveArticleNotify(userFoot, notifyType);
                log.info("通知消息保存成功 - 类型: {}, 目标用户: {}", notifyType, message.getTargetUserId());
            } else {
                log.warn("未找到用户足迹记录，无法保存通知消息");
            }
        } catch (Exception e) {
            log.error("保存通知消息失败", e);
        }
    }
    
    /**
     * 查找用户足迹记录
     */
    private UserFootDO findUserFoot(ArticleKafkaMessageDTO message) {
        try {
            // 这里需要根据消息中的信息查找对应的UserFoot记录
            // 由于Kafka消息中没有包含UserFoot的完整信息，我们需要通过其他方式查找
            // 暂时返回null，实际实现时需要根据业务需求调整
            log.warn("查找用户足迹记录功能待实现");
            return null;
        } catch (Exception e) {
            log.error("查找用户足迹记录失败", e);
            return null;
        }
    }

}

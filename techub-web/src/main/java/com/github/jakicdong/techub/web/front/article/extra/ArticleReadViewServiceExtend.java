package com.github.jakicdong.techub.web.front.article.extra;

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.enums.ArticleReadTypeEnum;
import com.github.jakicdong.techub.api.model.enums.user.UserAIStatEnum;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.BaseUserInfoDTO;
import com.github.jakicdong.techub.service.article.service.ArticlePayService;
import com.github.jakicdong.techub.web.config.GlobalViewConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

/*
* @author JakicDong
* @description 文章阅读的扩展服务支撑
* @time 2025/8/11 17:28
* - 用于控制文章阅读模式
* */
@Service
public class ArticleReadViewServiceExtend {
    @Autowired
    private GlobalViewConfig globalViewConfig;
    @Autowired
    private ArticlePayService articlePayService;


    public String formatArticleReadType(ArticleDTO article) {
        ArticleReadTypeEnum readType = ArticleReadTypeEnum.typeOf(article.getReadType());
        if (readType != null && readType != ArticleReadTypeEnum.NORMAL) {
            BaseUserInfoDTO user = ReqInfoContext.getReqInfo().getUser();
            if (readType == ArticleReadTypeEnum.STAR_READ) {
                // 星球用户阅读
                return mark(article, () -> user != null && (user.getUserId().equals(article.getAuthor())
                                || user.getStarStatus() == UserAIStatEnum.FORMAL),
                        globalViewConfig::getZsxqArticleReadCount);
            } else if (readType == ArticleReadTypeEnum.PAY_READ) {
                // 付费阅读
                return mark(article, () -> user != null && (user.getUserId().equals(article.getAuthor())
                                || articlePayService.hasPayed(article.getArticleId(), user.getUserId())),
                        globalViewConfig::getNeedPayArticleReadCount);
            } else if (readType == ArticleReadTypeEnum.LOGIN) {
                // 登录阅读
                return mark(article, () -> user != null, globalViewConfig::getNeedLoginArticleReadCount);
            }
        }

        article.setCanRead(true);
        return article.getContent();
    }

    private String mark(ArticleDTO article, Supplier<Boolean> condition, Supplier<String> percent) {
        if (condition.get()) {
            // 可以阅读
            article.setCanRead(true);
            return article.getContent();
        } else {
            // 不能阅读
            article.setCanRead(false);
            return article.getContent()
                    .substring(0, (int) (article.getContent().length() * Float.parseFloat(percent.get()) / 100));
        }
    }
}

package com.github.jakicdong.techub.web.front.article.rest;

/*
* @author JakicDong
* @description 文章REST接口 返回json格式数据
* @time 2025/8/28 11:48
*/

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.enums.DocumentTypeEnum;
import com.github.jakicdong.techub.api.model.enums.OperateTypeEnum;
import com.github.jakicdong.techub.api.model.vo.ResVo;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import com.github.jakicdong.techub.core.mdc.MdcDot;
import com.github.jakicdong.techub.core.permission.Permission;
import com.github.jakicdong.techub.core.permission.UserRole;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDO;
import com.github.jakicdong.techub.service.article.service.ArticleReadService;
import com.github.jakicdong.techub.service.user.service.UserFootService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RequestMapping(path = "article/api")
@RestController
public class ArticleRestController {
    @Autowired
    private ArticleReadService articleReadService;
    @Autowired
    private UserFootService userFootService;
    
    
    /*
    * @author JakicDong
    * @description 点赞,收藏等相关操作
    * @time 2025/8/28 12:05
    */

    @Permission(role = UserRole.LOGIN)
    @GetMapping(path = "favor")
    @MdcDot(bizCode = "#articleId")
    public ResVo<Boolean> favor(@RequestParam(name = "articleId") Long articleId,
                                @RequestParam(name = "type") Integer type) throws IOException, TimeoutException {

        OperateTypeEnum operate = OperateTypeEnum.fromCode(type);
        if (operate == OperateTypeEnum.EMPTY) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, type + "非法");
        }

        // 要求文章必须存在
        ArticleDO article = articleReadService.queryBasicArticle(articleId);
        if (article == null) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "文章不存在!");
        }

        // 更新用户与文章的点赞/收藏状态
        userFootService.favorArticleComment(DocumentTypeEnum.ARTICLE, articleId, article.getUserId(),
                ReqInfoContext.getReqInfo().getUserId(),
                operate);
        return ResVo.ok(true);

    }

}

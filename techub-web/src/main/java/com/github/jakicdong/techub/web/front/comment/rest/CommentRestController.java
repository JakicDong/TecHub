package com.github.jakicdong.techub.web.front.comment.rest;

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.ResVo;
import com.github.jakicdong.techub.api.model.vo.comment.CommentSaveReq;
import com.github.jakicdong.techub.api.model.vo.comment.dto.TopCommentDTO;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import com.github.jakicdong.techub.core.permission.Permission;
import com.github.jakicdong.techub.core.permission.UserRole;
import com.github.jakicdong.techub.core.util.NumUtil;
import com.github.jakicdong.techub.service.article.conveter.ArticleConverter;
import com.github.jakicdong.techub.service.article.repository.entity.ArticleDO;
import com.github.jakicdong.techub.service.article.service.ArticleReadService;
import com.github.jakicdong.techub.service.comment.repository.entity.CommentDO;
import com.github.jakicdong.techub.service.comment.service.CommentReadService;
import com.github.jakicdong.techub.service.comment.service.CommentWriteService;
import com.github.jakicdong.techub.web.component.TemplateEngineHelper;
import com.github.jakicdong.techub.web.front.article.vo.ArticleDetailVo;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/*
 * @author JakicDong
 * @description 评论接口
 * @time 2025/9/2 19:04
 */
@RestController
@RequestMapping(path = "comment/api")
public class CommentRestController {


    @Autowired
    private CommentReadService commentReadService;
    @Autowired
    private ArticleReadService articleReadService;
    @Autowired
    private CommentWriteService commentWriteService;
    @Autowired
    private TemplateEngineHelper templateEngineHelper;


    /**
     * 评论列表页
     *
     * @param articleId
     * @return
     */

    @ResponseBody
    @RequestMapping(path = "list")
    public ResVo<List<TopCommentDTO>> list(Long articleId, Long pageNum, Long pageSize) {
        if (NumUtil.nullOrZero(articleId)) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "文章id为空");
        }
        pageNum = Optional.ofNullable(pageNum).orElse(PageParam.DEFAULT_PAGE_NUM);
        pageSize = Optional.ofNullable(pageSize).orElse(PageParam.DEFAULT_PAGE_SIZE);
        List<TopCommentDTO> result = commentReadService.getArticleComments(articleId, PageParam.newPageInstance(pageNum, pageSize));
        return ResVo.ok(result);
    }

    /*
    * @author JakicDong
    * @description 保存评论接口
    * @time 2025/9/3 11:23
    */
    @Permission(role = UserRole.LOGIN)
    @PostMapping(path = "post")
    @ResponseBody
    public ResVo<String> save(@RequestBody CommentSaveReq req){

        //自己写的
        if (req.getArticleId() == null) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "文章id为空");
        }
        ArticleDO article = articleReadService.queryBasicArticle(req.getArticleId());
        if (article == null) {
            return ResVo.fail(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "文章不存在!");
        }
        // 保存评论
        req.setUserId(ReqInfoContext.getReqInfo().getUserId());
        req.setCommentContent(StringEscapeUtils.escapeHtml3(req.getCommentContent()));
        commentWriteService.saveComment(req);

        // 返回新的评论信息，用于实时更新详情也的评论列表
        ArticleDetailVo vo = new ArticleDetailVo();
        vo.setArticle(ArticleConverter.toDto(article));
        // 评论信息
        List<TopCommentDTO> comments = commentReadService.getArticleComments(req.getArticleId(), PageParam.newPageInstance());
        vo.setComments(comments);

        // 热门评论
        TopCommentDTO hotComment = commentReadService.queryHotComment(req.getArticleId());
        vo.setHotComment(hotComment);
        String content = templateEngineHelper.render("views/article-detail/comment/index", vo);
        return ResVo.ok(content);
    }




}
















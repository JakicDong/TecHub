package com.github.jakicdong.techub.web.front.article.view;

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.enums.ArticleReadTypeEnum;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleDTO;
import com.github.jakicdong.techub.api.model.vo.article.dto.ArticleOtherDTO;
import com.github.jakicdong.techub.api.model.vo.comment.dto.TopCommentDTO;
import com.github.jakicdong.techub.api.model.vo.recommend.SideBarDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserStatisticInfoDTO;
import com.github.jakicdong.techub.core.util.MarkdownConverter;
import com.github.jakicdong.techub.core.util.SpringUtil;
import com.github.jakicdong.techub.service.article.converter.PayConverter;
import com.github.jakicdong.techub.service.article.service.ArticlePayService;
import com.github.jakicdong.techub.service.article.service.ArticleReadService;
import com.github.jakicdong.techub.service.comment.service.CommentReadService;
import com.github.jakicdong.techub.service.sidebar.service.SidebarService;
import com.github.jakicdong.techub.service.user.service.UserService;
import com.github.jakicdong.techub.web.front.article.extra.ArticleReadViewServiceExtend;
import com.github.jakicdong.techub.web.front.article.vo.ArticleDetailVo;
import com.github.jakicdong.techub.web.global.BaseViewController;
import com.github.jakicdong.techub.web.global.SeoInjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/*
* @author JakicDong
* @description 文章详情接口
* @time 2025/8/9 19:34
*
*  * 文章
 *  todo: 所有的入口都放在一个Controller，导致功能划分非常混乱
 * ： 文章列表
 * ： 文章编辑
 * ： 文章详情
 * ---
 *  - 返回视图 view
 *  - 返回json数据
 *
*/
@Slf4j
@Controller
@RequestMapping("/article")
public class ArticleViewController extends BaseViewController {

    @Autowired
    private ArticleReadService articleService;

    @Autowired
    private CommentReadService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticlePayService articlePayService;
    @Autowired
    private SidebarService sidebarService;


    @Autowired
    private ArticleReadViewServiceExtend articleReadViewServiceExtend;

    @GetMapping("detail/{articleId}")
    public String detail(@PathVariable(name = "articleId") Long articleId, Model model) throws IOException {
        //todo 针对专栏文章做一个重定向

        ArticleDetailVo vo = new ArticleDetailVo();
        // 文章相关信息
        ArticleDTO articleDTO = articleService.queryFullArticleInfo(articleId, ReqInfoContext.getReqInfo().getUserId());
        // 根据文章类型，来自动处理文章类容
        String content = articleReadViewServiceExtend.formatArticleReadType(articleDTO);
        // 返回给前端页面时，转换为html格式
        articleDTO.setContent(MarkdownConverter.markdownToHtml(content));
        vo.setArticle(articleDTO);


        // 评论信息
        List<TopCommentDTO> comments = commentService.getArticleComments(articleId, PageParam.newPageInstance(1L, 10L));
        vo.setComments(comments);

//        // 热门评论
//        TopCommentDTO hotComment = commentService.queryHotComment(articleId);
//        vo.setHotComment(hotComment);
//
//
//        // 作者信息
//        UserStatisticInfoDTO user = userService.queryUserInfoWithStatistic(articleDTO.getAuthor());
//        articleDTO.setAuthorName(user.getUserName());
//        articleDTO.setAuthorAvatar(user.getPhoto());
//        if (articleDTO.getReadType().equals(ArticleReadTypeEnum.PAY_READ.getType())) {
//            // 付费阅读的文章，构建收款码信息
//            user.setPayQrCodes(PayConverter.formatPayCodeInfo(user.getPayCode()));
//        }
//        vo.setAuthor(user);
//
//        // 其他信息封装
//        ArticleOtherDTO other = new ArticleOtherDTO();
//        other.setReadType(articleDTO.getReadType());
//        vo.setOther(other);
//
//        // 打赏用户列表
//        if (Objects.equals(articleDTO.getReadType(), ArticleReadTypeEnum.PAY_READ.getType())) {
//            vo.setPayUsers(articlePayService.queryPayUsers(articleId));
//        } else {
//            vo.setPayUsers(Collections.emptyList());
//        }
//
//        // 详情页的侧边推荐信息
//        List<SideBarDTO> sideBars = sidebarService.queryArticleDetailSidebarList(articleDTO.getAuthor(), articleDTO.getArticleId());
//        vo.setSideBarItems(sideBars);


        model.addAttribute("vo", vo);

        SpringUtil.getBean(SeoInjectService.class).initColumnSeo(vo);

        return "views/article-detail/index";

    }

}













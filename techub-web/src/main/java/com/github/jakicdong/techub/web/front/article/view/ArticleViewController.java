package com.github.jakicdong.techub.web.front.article.view;

import com.github.jakicdong.techub.web.front.article.vo.ArticleDetailVo;
import com.github.jakicdong.techub.web.global.BaseViewController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

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


    @GetMapping("detail/{articleId}")
    public String detail(@PathVariable(name = "articleId") Long articleId, Model model) throws IOException {


        log.info("gogogo, articleId: {}", articleId);
        ArticleDetailVo vo = new ArticleDetailVo();


//        throw new RuntimeException("文章详情功能尚未实现，需要完善数据查询逻辑");
        return "views/article-detail/index";

    }

}

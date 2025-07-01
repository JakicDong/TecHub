package com.github.jakicdong.techub.web.front.home.helper;


/*
* @author JakicDong
* @description 首页推荐相关
* @time 2025/7/1 20:27
*/

import com.github.jakicdong.techub.web.front.home.vo.IndexVo;
import org.springframework.stereotype.Component;

@Component
public class IndexRecommendHelper {
    //todo 实现首页推荐相关逻辑

    public IndexVo buildIndexVo(String activeTab) {
        IndexVo vo = new IndexVo();
        //todo 暂时全都注释了
//        CategoryDTO category = categories(activeTab, vo);
//        vo.setCategoryId(category.getCategoryId());
//        vo.setCurrentCategory(category.getCategory());
//        // 并行调度实例，提高响应性能
//        AsyncUtil.concurrentExecutor("首页响应")
//                .async(() -> vo.setArticles(articleList(category.getCategoryId())), "文章列表")
//                .async(() -> vo.setTopArticles(topArticleList(category)), "置顶文章")
//                .async(() -> vo.setHomeCarouselList(homeCarouselList()), "轮播图")
//                .async(() -> vo.setSideBarItems(sidebarService.queryHomeSidebarList()), "侧边栏")
//                .async(() -> vo.setUser(loginInfo()), "用户信息")
//                .allExecuted()
//                .prettyPrint();



        return vo;
    }

}

package com.github.jakicdong.techub.web.front.home.helper;


/*
* @author JakicDong
* @description 首页推荐相关
* @time 2025/7/1 20:27
*/

import com.github.jakicdong.techub.api.model.vo.article.dto.CategoryDTO;
import com.github.jakicdong.techub.service.article.service.CategoryService;
import com.github.jakicdong.techub.web.front.home.vo.IndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class IndexRecommendHelper {
    @Autowired
    private CategoryService categoryService;

//    @Autowired
//    private ArticleReadService articleService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private SidebarService sidebarService;
//
//    @Autowired
//    private ConfigService configService;

//
//    public IndexVo buildIndexVo(String activeTab) {
//        IndexVo vo = new IndexVo();
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
//        return vo;
//    }

//    /**
//     * 返回分类列表
//     *
//     * @param active 选中的分类
//     * @param vo     返回结果
//     * @return 返回选中的分类；当没有匹配时，返回默认的全部分类
//     */
//    private CategoryDTO categories(String active, IndexVo vo) {
//        List<CategoryDTO> allList = categoryService.loadAllCategories();
//        // 查询所有分类的对应的文章数
//        Map<Long, Long> articleCnt = articleService.queryArticleCountsByCategory();
//        // 过滤掉文章数为0的分类
//        allList.removeIf(c -> articleCnt.getOrDefault(c.getCategoryId(), 0L) <= 0L);
//
//        // 刷新选中的分类
//        AtomicReference<CategoryDTO> selectedArticle = new AtomicReference<>();
//        allList.forEach(category -> {
//            if (category.getCategory().equalsIgnoreCase(active)) {
//                category.setSelected(true);
//                selectedArticle.set(category);
//            } else {
//                category.setSelected(false);
//            }
//        });
//
//        // 添加默认的全部分类
//        allList.add(0, new CategoryDTO(0L, CategoryDTO.DEFAULT_TOTAL_CATEGORY));
//        if (selectedArticle.get() == null) {
//            selectedArticle.set(allList.get(0));
//            allList.get(0).setSelected(true);
//        }
//
//        vo.setCategories(allList);
//        return selectedArticle.get();
//    }

}

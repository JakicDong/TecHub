package com.github.jakicdong.techub.web.front.home;

import com.github.jakicdong.techub.web.front.home.helper.IndexRecommendHelper;
import com.github.jakicdong.techub.web.front.home.vo.IndexVo;
import com.github.jakicdong.techub.web.global.BaseViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/*
* @author JakicDong
* @description 主页的Controller
* @time 2025/7/1 20:10
*/
@Controller
public class IndexController extends BaseViewController {
    @Autowired
    private IndexRecommendHelper indexRecommendHelper;

    @GetMapping(path = {"/", "", "/index", "/login"})
    public String index(Model model, HttpServletRequest request) {
        String activeTab = request.getParameter("category");
        IndexVo vo = indexRecommendHelper.buildIndexVo(activeTab);
        model.addAttribute("vo", vo);
        return "views/home/index";
    }

}

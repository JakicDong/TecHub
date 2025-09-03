package com.github.jakicdong.techub.web.front.article.view;


import com.github.jakicdong.techub.api.model.vo.PageListVo;
import com.github.jakicdong.techub.api.model.vo.PageParam;
import com.github.jakicdong.techub.api.model.vo.article.dto.ColumnDTO;
import com.github.jakicdong.techub.api.model.vo.recommend.SideBarDTO;
import com.github.jakicdong.techub.service.article.service.ColumnService;
import com.github.jakicdong.techub.service.sidebar.service.SidebarService;
import com.github.jakicdong.techub.web.front.article.vo.ColumnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/*
* @author JakicDong
* @description 专栏入口
* @time 2025/9/3 15:55
 */

@Controller
@RequestMapping(path = "column")
public class ColumnViewController {
    @Autowired
    private ColumnService columnService;

    @Autowired
    private SidebarService sidebarService;


    /**
     * 专栏主页，展示专栏列表
     *
     * @param model
     * @return
     */
    @GetMapping(path = {"list", "/", "", "home"})
    public String list(Model model) {
        PageListVo<ColumnDTO> columns = columnService.listColumn(PageParam.newPageInstance());
        List<SideBarDTO> sidebars = sidebarService.queryColumnSidebarList();
        ColumnVo vo = new ColumnVo();
        vo.setColumns(columns);
        vo.setSideBarItems(sidebars);
        model.addAttribute("vo", vo);
        return "views/column-home/index";
    }


}

package com.github.jakicdong.techub.web.global;


/*
* @author JakicDong
* @description 全局属性配置
* @time 2025/7/1 20:11
*/
import com.github.jakicdong.techub.api.model.vo.PageParam;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseViewController {
    @Autowired
    protected GlobalInitService globalInitService;

    public PageParam buildPageParam(Long page, Long size) {
        if (page <= 0) {
            page = PageParam.DEFAULT_PAGE_NUM;
        }
        if (size == null || size > PageParam.DEFAULT_PAGE_SIZE) {
            size = PageParam.DEFAULT_PAGE_SIZE;
        }
        return PageParam.newPageInstance(page, size);
    }
}

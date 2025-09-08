package com.github.jakicdong.techub.api.model.vo;


import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/*
* @author JakicDong
* @description 分页列表
* @time 2025/7/3 14:13
*/
@Data
public class PageListVo<T> {
    /**
     * 用户列表
     */
    List<T> list;

    /**
     * 是否有更多
     */
    private Boolean hasMore;

    public static <T> PageListVo<T> emptyVo() {
        PageListVo<T> vo = new PageListVo<>();
        vo.setList(Collections.emptyList());
        vo.setHasMore(false);
        return vo;
    }

    public static <T> PageListVo<T> newVo(List<T> list, long pageSize) {
        PageListVo<T> vo = new PageListVo<>();
        vo.setList(Optional.ofNullable(list).orElse(Collections.emptyList()));
        //如果拿回来的数量等于页面大小，说明还有更多
        vo.setHasMore(vo.getList().size() == pageSize);
        return vo;
    }
}

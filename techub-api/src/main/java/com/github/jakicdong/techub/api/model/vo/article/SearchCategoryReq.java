package com.github.jakicdong.techub.api.model.vo.article;

import lombok.Data;

/*
* @author JakicDong
* @description
* @time 2025/9/11 15:39
*/
@Data
public class SearchCategoryReq {
    // 类目名称
    private String category;
    // 分页
    private Long pageNumber;
    private Long pageSize;

}

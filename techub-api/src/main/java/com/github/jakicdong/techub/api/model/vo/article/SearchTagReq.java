package com.github.jakicdong.techub.api.model.vo.article;

import lombok.Data;

/*
* @author JakicDong
* @description 标签查询
* @time 2025/9/11 15:39
*/
@Data
public class SearchTagReq {
    // 标签名称
    private String tag;
    // 分页
    private Long pageNumber;
    private Long pageSize;
}

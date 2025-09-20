package com.github.jakicdong.techub.api.model.vo.banner;

import lombok.Data;

/*
* @author JakicDong
* @description 搜索配置请求参数
* @time 2025/9/20 18:40
*/

@Data
public class SearchConfigReq {
    /**
    * 类型
    */
    private Integer type;

    /**
    * 名称
    */
    private String name;

    /**
     * 分页
     */
    private Long pageNumber;
    private Long pageSize;

}

package com.github.jakicdong.techub.api.model.vo.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
* @author JakicDong
* @description 教程查询
* @time 2025/9/11 15:39
*/
@Data
@ApiModel("教程查询")
public class SearchColumnReq {

    // 教程名称
    @ApiModelProperty("教程名称")
    private String column;

    @ApiModelProperty("请求页数，从1开始计数")
    private long pageNumber;

    @ApiModelProperty("请求页大小，默认为 10")
    private long pageSize;
}

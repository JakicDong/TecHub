package com.github.jakicdong.techub.api.model.vo.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description 用户基本信息
* @time 2025/7/3 14:35
*/
@Data
public class SimpleUserInfoDTO implements Serializable {
    private static final long serialVersionUID = 4802653694786272120L;

    @ApiModelProperty("作者ID")
    private Long userId;

    @ApiModelProperty("作者名")
    private String name;

    @ApiModelProperty("作者头像")
    private String avatar;

    @ApiModelProperty("作者简介")
    private String profile;
}

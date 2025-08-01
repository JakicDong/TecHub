package com.github.jakicdong.techub.api.model.vo.user.dto;


import com.github.jakicdong.techub.api.model.entity.BaseDTO;
import com.github.jakicdong.techub.api.model.enums.user.UserAIStatEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/*
* @author JakicDong
* @description 用户基础信息DTO
* @time 2025/7/3 14:16
*/
@Data
@ApiModel("用户基础实体对象")
@Accessors(chain = true)
public class BaseUserInfoDTO extends BaseDTO {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    /**
     * 用户角色 admin, normal
     */
    @ApiModelProperty(value = "角色", example = "ADMIN|NORMAL")
    private String role;

    /**
     * 用户图像
     */
    @ApiModelProperty(value = "用户头像")
    private String photo;
    /**
     * 个人简介
     */
    @ApiModelProperty(value = "用户简介")
    private String profile;
    /**
     * 职位
     */
    @ApiModelProperty(value = "个人职位")
    private String position;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private String company;

    /**
     * 扩展字段
     */
    @ApiModelProperty(hidden = true)
    private String extend;

    /**
     * 是否删除
     */
    @ApiModelProperty(hidden = true, value = "用户是否被删除")
    private Integer deleted;

    /**
     * 用户最后登录区域
     */
    @ApiModelProperty(value = "用户最后登录的地理位置", example = "湖北·武汉")
    private String region;

    /**
     * 星球状态
     */
    private UserAIStatEnum starStatus;

    /**
     * 用户的邮箱
     */
    @ApiModelProperty(value = "用户邮箱", example = "paicoding@126.com")
    private String email;

    /**
     * 收款码信息
     */
    @ApiModelProperty(value = "用户的收款码", example = "{\"wx\":\"wxp://f2f0YUXuGn6X2dI6FS2GrMjuG0Lw2plZqwjO4keoZaRr320\"}")
    private String payCode;
}

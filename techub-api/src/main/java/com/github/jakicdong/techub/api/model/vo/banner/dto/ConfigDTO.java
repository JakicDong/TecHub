package com.github.jakicdong.techub.api.model.vo.banner.dto;

import com.github.jakicdong.techub.api.model.entity.BaseDTO;
import com.github.jakicdong.techub.api.model.enums.ConfigTagEnum;
import lombok.Data;
/*
* @author JakicDong
* @description Banner横幅
* @time 2025/7/3 18:38
*/
@Data
public class ConfigDTO extends BaseDTO {
    /**
     * 类型
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片链接
     */
    private String bannerUrl;

    /**
     * 跳转链接
     */
    private String jumpUrl;

    /**
     * 内容
     */
    private String content;

    /**
     * 排序
     */
    private Integer rank;

    /**
     * 状态：0-未发布，1-已发布
     */
    private Integer status;

    /**
     * json格式扩展信息
     */
    private String extra;

    /**
     * 配置相关的标签：如 火，推荐，精选 等等，英文逗号分隔
     *
     * @see ConfigTagEnum#getCode()
     */
    private String tags;
}

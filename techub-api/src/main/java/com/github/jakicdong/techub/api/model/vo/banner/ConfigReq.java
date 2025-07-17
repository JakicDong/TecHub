package com.github.jakicdong.techub.api.model.vo.banner;

/*
* @author JakicDong
* @description 保存banner请求参数
* @time 2025/7/3 19:15
*/

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigReq  implements Serializable {

    /**
     * ID
     */
    private Long configId;

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
     * 标签
     */
    private String tags;

}

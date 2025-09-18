package com.github.jakicdong.techub.service.sitemap.model;

import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description 站点地图计数实体类
* @time 2025/9/18 16:10
*/
@Data
public class SiteCntVo implements Serializable {
    private static final long serialVersionUID = 8747459624770066661L;
    /**
     * 日期
     */
    private String day;
    /**
     * 路径，全站时，path为null
     */
    private String path;
    /**
     * 站点page view 点击数
     */
    private Integer pv;
    /**
     * 站点unique view 点击数
     */
    private Integer uv;
}

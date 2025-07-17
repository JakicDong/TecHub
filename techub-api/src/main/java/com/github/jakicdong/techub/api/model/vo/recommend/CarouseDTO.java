package com.github.jakicdong.techub.api.model.vo.recommend;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
/*
* @author JakicDong
* @description 轮播图
* @time 2025/7/3 14:48
*/
@Data
@Accessors(chain = true)
public class CarouseDTO implements Serializable {

    private static final long serialVersionUID = 1048555496974144842L;
    /**
     * 说明
     */
    private String name;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 跳转地址
     */
    private String actionUrl;
}

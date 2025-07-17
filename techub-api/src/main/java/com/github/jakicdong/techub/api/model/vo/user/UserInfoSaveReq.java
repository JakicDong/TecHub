package com.github.jakicdong.techub.api.model.vo.user;

/*
* @author JakicDong
* @description 用户信息入参
* @time 2025/7/3 19:51
*/

import lombok.Data;

import java.util.Map;

@Data
public class UserInfoSaveReq {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户图像
     */
    private String photo;

    /**
     * 职位
     */
    private String position;

    /**
     * 公司
     */
    private String company;

    /**
     * 个人简介
     */
    private String profile;

    /**
     * 用户的邮件地址
     */
    private String email;

    /**
     * 收款码
     * key: qq|wx|ali --> 收款渠道
     * value: 收款二维码内容
     */
    private Map<String, String> payCode;
}

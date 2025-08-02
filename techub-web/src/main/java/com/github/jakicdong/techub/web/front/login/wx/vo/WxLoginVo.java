package com.github.jakicdong.techub.web.front.login.wx.vo;


import lombok.Data;

/*
* @author JakicDong
* @description 微信扫码登录的VO
* @time 2025/7/31 14:29
*/
@Data
public class WxLoginVo {
    /**
     * 验证码
     */
    private String code;

    /**
     * 二维码
     */
    private String qr;

    /**
     * true 表示需要重新建立连接
     */
    private boolean reconnect;
}

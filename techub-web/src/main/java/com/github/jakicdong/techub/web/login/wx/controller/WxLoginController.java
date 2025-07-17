package com.github.jakicdong.techub.web.login.wx.controller;

import com.github.jakicdong.techub.core.mdc.MdcDot;
import com.github.jakicdong.techub.web.front.login.wx.helper.WxLoginHelper;
import com.github.jakicdong.techub.web.global.BaseViewController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/*
* @author JakicDong
* @description 微信登录的入口
* @time 2025/7/11 11:22
*/

@Controller
@Slf4j
public class WxLoginController extends BaseViewController {
    @Autowired
    private WxLoginHelper qrLoginHelper;

    /**
     * 客户端与后端建立扫描二维码的长连接
     *
     * @return
     */
    @MdcDot
    @ResponseBody
    @GetMapping(path = "subscribe", produces = {org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter subscribe(String deviceId) throws IOException {
        return qrLoginHelper.subscribe();
    }

}

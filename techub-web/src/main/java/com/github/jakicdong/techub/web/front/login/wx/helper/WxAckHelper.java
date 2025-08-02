package com.github.jakicdong.techub.web.front.login.wx.helper;

import com.github.jakicdong.techub.api.model.vo.user.wx.BaseWxMsgResVo;
import com.github.jakicdong.techub.api.model.vo.user.wx.WxImgTxtItemVo;
import com.github.jakicdong.techub.api.model.vo.user.wx.WxImgTxtMsgResVo;
import com.github.jakicdong.techub.api.model.vo.user.wx.WxTxtMsgResVo;
import com.github.jakicdong.techub.core.util.CodeGenerateUtil;
import com.github.jakicdong.techub.service.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/*
* @author JakicDong
* @description 微信请求回复helper
* @time 2025/7/31 15:51
*/
@Slf4j
@Component
public class WxAckHelper {
    @Autowired
    private LoginService sessionService;
    @Autowired
    private WxLoginHelper qrLoginHelper;

    public BaseWxMsgResVo buildResponseBody(String eventType, String content, String fromUser) {
        // 返回的文本消息
        String textRes = null;
        // 返回的是图文消息
        List<WxImgTxtItemVo> imgTxtList = null;
        if ("subscribe".equalsIgnoreCase(eventType)) {
            // 订阅
            textRes = "欢迎关注,一个学习Java后端的学习者\n" +
            "个人博客地址:https://jakicdong.github.io/ \n" +
            "个人github仓库:https://github.com/JakicDong/ \n ";
            log.info("有人订阅");
        }// 微信公众号登录
        else if (CodeGenerateUtil.isVerifyCode(content)) {
            sessionService.autoRegisterWxUserInfo(fromUser);
            if (qrLoginHelper.login(content)) {
                textRes = "登录成功，开始愉快的使用TecHub！";
            } else {
                textRes = "验证码过期了，刷新登录页面重试一下吧";
            }
        }else {
            textRes =
                    "个人博客地址:https://jakicdong.github.io/ \n" +
                    "个人github仓库:https://github.com/JakicDong/ \n ";
        }


        if (textRes != null) {
            WxTxtMsgResVo vo = new WxTxtMsgResVo();
            vo.setContent(textRes);
            log.info(vo.toString());
            return vo;
        } else {
            WxImgTxtMsgResVo vo = new WxImgTxtMsgResVo();
            vo.setArticles(imgTxtList);
            vo.setArticleCount(imgTxtList.size());
            return vo;
        }
    }
}

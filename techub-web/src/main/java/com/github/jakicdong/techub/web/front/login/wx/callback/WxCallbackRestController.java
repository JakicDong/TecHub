package com.github.jakicdong.techub.web.front.login.wx.callback;


import com.github.jakicdong.techub.api.model.vo.user.wx.BaseWxMsgResVo;
import com.github.jakicdong.techub.api.model.vo.user.wx.WxTxtMsgReqVo;
import com.github.jakicdong.techub.api.model.vo.user.wx.WxTxtMsgResVo;
import com.github.jakicdong.techub.service.user.service.LoginService;
import com.github.jakicdong.techub.web.front.login.wx.helper.WxAckHelper;
import com.github.jakicdong.techub.web.front.login.wx.helper.WxLoginHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping(path = "wx")
@RestController
public class WxCallbackRestController {

    @Autowired
    private LoginService sessionService;
    @Autowired
    private WxLoginHelper qrLoginHelper;
    @Autowired
    private WxAckHelper wxHelper;

    /**
     * 微信的公众号接入 token 验证，即返回echostr的参数值
     *
     * @param request
     * @return
     */
    @GetMapping(path = "callback")
    public String check(HttpServletRequest request) {
        String echoStr = request.getParameter("echostr");
        if (StringUtils.isNoneEmpty(echoStr)) {
            return echoStr;
        }
        return "";
    }


    @PostMapping(path = "callback",
            consumes = {"application/xml", "text/xml"},
            produces = "application/xml;charset=utf-8")
    public BaseWxMsgResVo callBack(@RequestBody WxTxtMsgReqVo msg) {
        String content = msg.getContent();
        if ("subscribe".equals(msg.getEvent()) || "scan".equalsIgnoreCase(msg.getEvent())) {
            String key = msg.getEventKey();
            //扫码登录的逻辑
            if (StringUtils.isNotBlank(key) && key.startsWith("qrscene_")) {
                // 带参数的二维码，扫描、关注事件拿到之后，直接登录，省却输入验证码这一步
                // fixme 带参数二维码需要 微信认证，个人公众号无权限
                String code = key.substring("qrscene_".length());

                sessionService.autoRegisterWxUserInfo(msg.getFromUserName());
                qrLoginHelper.login(code);
                WxTxtMsgResVo res = new WxTxtMsgResVo();
                res.setContent("登录成功");
                fillResVo(res, msg);
                return res;
            }
        }
        log.info("WXcallBack");
        BaseWxMsgResVo res = wxHelper.buildResponseBody(msg.getEvent(), content, msg.getFromUserName());
        fillResVo(res, msg);
        return res;
    }

    private void fillResVo(BaseWxMsgResVo res, WxTxtMsgReqVo msg) {
        res.setFromUserName(msg.getToUserName());
        res.setToUserName(msg.getFromUserName());
        res.setCreateTime(System.currentTimeMillis() / 1000);
    }

}

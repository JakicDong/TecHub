package com.github.jakicdong.techub.web.front.notice.rest;

/*
* @author JakicDong
* @description 全局通知接口
* @time 2025/9/8 19:54
*/

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.vo.ResVo;
import com.github.jakicdong.techub.api.model.vo.user.dto.BaseUserInfoDTO;
import com.github.jakicdong.techub.core.permission.Permission;
import com.github.jakicdong.techub.core.permission.UserRole;
import com.github.jakicdong.techub.core.ws.WebSocketResponseUtil;
import com.github.jakicdong.techub.service.notify.service.NotifyService;
import com.github.jakicdong.techub.web.component.TemplateEngineHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Permission(role = UserRole.LOGIN)
@RestController
@RequestMapping(path = "notice/api")
public class NoticeRestController {
    @Autowired
    private TemplateEngineHelper templateEngineHelper;

    private NotifyService notifyService;



    /**
     * 给自己发送通知消息 -- 用于测试消息通知
     *
     * @param content
     * @return
     */
    @RequestMapping(path = "notifyToSelf")
    public ResVo<Boolean> notifyToSelf(String content) {
        notifyService.notifyToUser(ReqInfoContext.getReqInfo().getUserId(), content);
        return ResVo.ok(true);
    }

    /**
     * 发送广播消息
     *
     * @param content
     * @return
     */
    @RequestMapping(path = "notifyToAll")
    public ResVo<Boolean> notifyToAll(String content) {
        BaseUserInfoDTO user = ReqInfoContext.getReqInfo().getUser();
        WebSocketResponseUtil.broadcastMsg(NotifyService.NOTIFY_TOPIC, String.format("【%s】发送了一条广播消息: %s", user.getUserName(), content));
        return ResVo.ok(true);
    }
}

package com.github.jakicdong.techub.web.front.chat.helper;

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.enums.ai.AISourceEnum;
import com.github.jakicdong.techub.core.mdc.MdcUtil;
import com.github.jakicdong.techub.service.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/*
* @author JakicDong
* @description ws工具类
* @time 2025/9/8 20:34
 */
@Slf4j
@Component
public class WsAnswerHelper {
    public static final String AI_SOURCE_PARAM = "AI";

    //todo chat部分的ws长连接代码
//    @Autowired
//    private ChatFacade chatFacade;
//
//    private void sendMsgToUser(String session, String question) {
//        ChatRecordsVo res = chatFacade.autoChat(question, vo -> response(session, vo));
//        log.info("AI直接返回：{}", res);
//    }
//
//    public void sendMsgToUser(AISourceEnum ai, String session, String question) {
//        if (ai == null) {
//            // 自动选择AI类型
//            sendMsgToUser(session, question);
//        } else {
//            ChatRecordsVo res = chatFacade.autoChat(ai, question, vo -> response(session, vo));
//            log.info("AI直接返回：{}", res);
//        }
//    }
//
//    public void sendMsgHistoryToUser(String session, AISourceEnum ai) {
//        ChatRecordsVo vo = chatFacade.history(ai);
//        response(session, vo);
//    }

    //todo 将结果返回用户
//    /**
//     * 将返回结果推送给用户
//     *
//     * @param session
//     * @param response
//     */
//    public void response(String session, ChatRecordsVo response) {
//        // convertAndSendToUser 方法可以发送信给给指定用户,
//        // 底层会自动将第二个参数目的地址 /chat/rsp 拼接为
//        // /user/username/chat/rsp，其中第二个参数 username 即为这里的第一个参数 session
//        // username 也是AuthHandshakeHandler中配置的 Principal 用户识别标志
//        WebSocketResponseUtil.sendMsgToUser(session, "/chat/rsp", response);
//    }

    public void execute(Map<String, Object> attributes, Runnable func) {
        try {
            ReqInfoContext.ReqInfo reqInfo = (ReqInfoContext.ReqInfo) attributes.get(LoginService.SESSION_KEY);
            ReqInfoContext.addReqInfo(reqInfo);
            String traceId = (String) attributes.get(MdcUtil.TRACE_ID_KEY);
            MdcUtil.add(MdcUtil.TRACE_ID_KEY, traceId);


            // 执行具体的业务逻辑
            func.run();

        } finally {
            ReqInfoContext.clear();
            MdcUtil.clear();
        }
    }
}

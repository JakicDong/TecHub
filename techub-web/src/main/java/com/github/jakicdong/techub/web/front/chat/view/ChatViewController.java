package com.github.jakicdong.techub.web.front.chat.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
* @author JakicDong
* @description 聊天页面
* @time 2025/9/20 20:36
*/
@Controller
@RequestMapping(path = "chat")
public class ChatViewController {
    @RequestMapping(path = {"", "/", "home"})
    public String index() {
        return "views/chat-home/index";
    }
}

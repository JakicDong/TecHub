package com.github.jakicdong.techub.api.model.vo.user.wx;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/*
* @author JakicDong
* @description 微信消息返回的数据结构体
* @time 2025/7/31 15:44
* @link <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Passive_user_reply_message.html"/>
 *
 */
@Data
@JacksonXmlRootElement(localName = "xml")
public class BaseWxMsgResVo {

    @JacksonXmlProperty(localName = "ToUserName")
    private String toUserName;
    @JacksonXmlProperty(localName = "FromUserName")
    private String fromUserName;
    @JacksonXmlProperty(localName = "CreateTime")
    private Long createTime;
    @JacksonXmlProperty(localName = "MsgType")
    private String msgType;
}

package com.github.jakicdong.techub.api.model.vo.user.wx;

/*
* @author JakicDong
* @description 微信返回的数据结构体
* @time 2025/7/31 15:49
* @link <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Passive_user_reply_message.html"/>
 *
 */

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@JacksonXmlRootElement(localName = "xml")
public class WxTxtMsgResVo extends BaseWxMsgResVo {
    @JacksonXmlProperty(localName = "Content")
    private String content;

    public WxTxtMsgResVo() {
        setMsgType("text");
    }
}

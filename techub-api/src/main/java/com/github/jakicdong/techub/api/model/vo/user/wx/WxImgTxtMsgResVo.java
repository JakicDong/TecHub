package com.github.jakicdong.techub.api.model.vo.user.wx;

/*
* @author JakicDong
* @description 返回的数据结构体
* @time 2025/7/31 16:33
* @link <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Passive_user_reply_message.html"/>
 */

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@JacksonXmlRootElement(localName = "xml")
public class WxImgTxtMsgResVo extends BaseWxMsgResVo {
    @JacksonXmlProperty(localName = "ArticleCount")
    private Integer articleCount;
    @JacksonXmlElementWrapper(localName = "Articles")
    @JacksonXmlProperty(localName = "item")
    private List<WxImgTxtItemVo> articles;

    public WxImgTxtMsgResVo() {
        setMsgType("news");
    }
}

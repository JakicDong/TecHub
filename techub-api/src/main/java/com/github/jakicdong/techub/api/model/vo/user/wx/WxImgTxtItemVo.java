package com.github.jakicdong.techub.api.model.vo.user.wx;

/*
* @author JakicDong
* @description 返回微信的数据结构体
* @time 2025/7/31 16:27
* @link <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Passive_user_reply_message.html#%E5%9B%9E%E5%A4%8D%E5%9B%BE%E6%96%87%E6%B6%88%E6%81%AF"/>
 *
 */

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "item")
public class WxImgTxtItemVo {

    @JacksonXmlProperty(localName = "Title")
    private String title;
    @JacksonXmlProperty(localName = "Description")
    private String description;
    @JacksonXmlProperty(localName = "PicUrl")
    private String picUrl;
    @JacksonXmlProperty(localName = "Url")
    private String url;
}

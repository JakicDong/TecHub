package com.github.jakicdong.techub.service.pay.model;

import com.github.jakicdong.techub.api.model.enums.pay.ThirdPayWayEnum;
import lombok.Data;
import lombok.experimental.Accessors;


/*
* @author JakicDong
* @description 向三方支付平台下单的请求业务参数
* @time 2025/9/20 19:55
*/
@Data
@Accessors(chain = true)
public class ThirdPayOrderReqBo {
    /**
     * 订单号（业务）
     */
    String outTradeNo;
    /**
     * 用户openId, 对于h5支付场景下，没有这个参数
     */
    String openId;
    /**
     * 订单描述
     */
    String description;
    /**
     * 订单总金额，单位为分
     */
    int total;

    /**
     * 支付方式
     */
    ThirdPayWayEnum payWay;

    public ThirdPayOrderReqBo() {
    }

    public ThirdPayOrderReqBo(String outTradeNo, String description, int total) {
        this.outTradeNo = outTradeNo;
        this.description = description;
        this.total = total;
    }

    public ThirdPayOrderReqBo(String outTradeNo, String openId, String description, int total) {
        this.outTradeNo = outTradeNo;
        this.openId = openId;
        this.description = description;
        this.total = total;
    }
}

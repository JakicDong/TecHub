package com.github.jakicdong.techub.service.pay.model;

import com.github.jakicdong.techub.api.model.enums.pay.PayStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/*
* @author JakicDong
* @description 支付回调通知业务对象
* @time 2025/9/20 19:54
*/
@Data
@Accessors(chain = true)
public class PayCallbackBo {
    /**
     * 传递给支付系统的唯一外部单号
     */
    private String outTradeNo;
    /**
     * 对应系统内的业务支付id
     */
    private Long payId;
    /**
     * 支付成功时间
     */
    private Long successTime;

    /**
     * 三方流水编号
     */
    private String thirdTransactionId;

    /**
     * 支付状态
     */
    private PayStatusEnum payStatus;
}

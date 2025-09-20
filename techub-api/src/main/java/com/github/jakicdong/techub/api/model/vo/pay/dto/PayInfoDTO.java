package com.github.jakicdong.techub.api.model.vo.pay.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/*
* @author JakicDong
* @description 用于支付的相关信息
* @time 2025/9/20 19:57
*/
@Data
public class PayInfoDTO implements Serializable {
    /**
     * 收款用户对应的各渠道的收款码
     */
    private Map<String, String> payQrCodeMap;

    /**
     * 支付方式
     */
    private String payWay;

    /**
     * 支付金额
     */
    private String payAmount;

    /**
     * 支付信息
     */
    private String prePayId;

    /**
     * 失效时间
     */
    private Long prePayExpireTime;
}

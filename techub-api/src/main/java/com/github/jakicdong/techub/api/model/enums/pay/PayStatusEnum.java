package com.github.jakicdong.techub.api.model.enums.pay;

import lombok.Getter;

import java.util.Objects;

/*
* @author JakicDong
* @description 支付状态
* @time 2025/8/11 17:32
*/
@Getter
public enum PayStatusEnum {

    NOT_PAY(0, "未支付"),

    PAYING(1, "支付中"),

    SUCCEED(2, "支付成功"),

    FAIL(3, "支付失败"),
    ;

    private Integer status;
    private String msg;

    PayStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static PayStatusEnum statusOf(Integer status) {
        for (PayStatusEnum p : values()) {
            if (Objects.equals(status, p.status)) {
                return p;
            }
        }
        return null;
    }
}

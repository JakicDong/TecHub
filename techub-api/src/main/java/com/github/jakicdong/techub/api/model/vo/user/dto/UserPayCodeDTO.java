package com.github.jakicdong.techub.api.model.vo.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* @author JakicDong
* @description 用户收款码信息
* @time 2025/7/3 14:39
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPayCodeDTO implements Serializable {
    private static final long serialVersionUID = -2601714252107169062L;

    /**
     * base64格式的收款二维码图片
     */
    private String qrCode;

    /**
     * 内容
     */
    private String qrMsg;
}

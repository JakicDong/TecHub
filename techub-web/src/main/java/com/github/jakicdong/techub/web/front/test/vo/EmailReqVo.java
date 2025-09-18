package com.github.jakicdong.techub.web.front.test.vo;

import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description 邮件发送验证vo
* @time 2025/9/18 11:18
*/
@Data
public class EmailReqVo implements Serializable {
    private static final long serialVersionUID = -8560585303684975482L;

    private String to;

    private String title;

    private String content;

}

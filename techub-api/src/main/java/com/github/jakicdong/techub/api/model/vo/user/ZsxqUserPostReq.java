package com.github.jakicdong.techub.api.model.vo.user;

import lombok.Data;

import java.io.Serializable;

/*
* @author JakicDong
* @description 发布用户req
* @time 2025/9/20 18:45
*/
@Data
public class ZsxqUserPostReq implements Serializable {
    // id
    private Long id;
    // 用户名
    private String userCode;
    // 用户昵称
    private String name;
    // 星球编号
    private String starNumber;
    // AI 策略
    private Integer strategy;
}

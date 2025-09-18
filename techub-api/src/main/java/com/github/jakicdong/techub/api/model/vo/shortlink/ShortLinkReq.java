package com.github.jakicdong.techub.api.model.vo.shortlink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
* @author JakicDong
* @description 短链接请求对象
* @time 2025/9/18 11:24
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkReq {
    /**
     * 原始URL
     */
    private String originalUrl;
}
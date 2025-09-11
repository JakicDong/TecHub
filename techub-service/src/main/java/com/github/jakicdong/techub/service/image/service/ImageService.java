package com.github.jakicdong.techub.service.image.service;



/*
* @author JakicDong
* @description 图片服务接口
* @time 2025/9/10 16:29
*/

import javax.servlet.http.HttpServletRequest;

public interface ImageService {

    /**
     * 图片转存
     * @param content
     * @return
     */
    String mdImgReplace(String content);

    /**
     * 外网图片转存
     *
     * @param img
     * @return
     */
    String saveImg(String img);

    /**
     * 保存图片
     *
     * @param request
     * @return
     */
    String saveImg(HttpServletRequest request);


}

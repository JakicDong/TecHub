package com.github.jakicdong.techub.service.image.service;



/*
* @author JakicDong
* @description 图片服务接口
* @time 2025/9/10 16:29
*/

import javax.servlet.http.HttpServletRequest;

public interface ImageService {


    /**
     * 保存图片
     *
     * @param request
     * @return
     */
    String saveImg(HttpServletRequest request);


}

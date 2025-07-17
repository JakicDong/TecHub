package com.github.jakicdong.techub.service.user.service;

/*
* @author JakicDong
* @description 用户注册服务
* @time 2025/7/9 14:48
*/

import com.github.jakicdong.techub.api.model.vo.user.UserPwdLoginReq;

public interface RegisterService {



    /**
     * 通过用户名/密码进行注册
     *
     * @param loginReq
     * @return
     */
    Long registerByUserNameAndPassword(UserPwdLoginReq loginReq);
}

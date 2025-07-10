package com.github.jakicdong.techub.service.user.service;

/*
* @author JakicDong
* @description 登录服务
* @time 2025/7/4 17:05
*/

import com.github.jakicdong.techub.api.model.vo.user.UserPwdLoginReq;

public interface LoginService {
    String SESSION_KEY = "f-session";
    String USER_DEVICE_KEY = "f-device";


    /**
     * 用户名密码方式登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    String loginByUserPwd(String username, String password);


    /**
     * 注册登录，并绑定对应的星球、邀请码
     *
     * @param loginReq 登录信息
     * @return
     */
    String registerByUserPwd(UserPwdLoginReq loginReq);

}

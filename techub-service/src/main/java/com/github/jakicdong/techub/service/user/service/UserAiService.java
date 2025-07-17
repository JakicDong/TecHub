package com.github.jakicdong.techub.service.user.service;

/*
* @author JakicDong
* @description 用户AI相关服务
* @time 2025/7/9 15:07
*/

import com.github.jakicdong.techub.api.model.vo.user.UserPwdLoginReq;

public interface UserAiService {

    /**
     * 重建用户绑定的星球编号
     *
     * @param loginReq
     */

    void initOrUpdateAiInfo(UserPwdLoginReq loginReq);

}

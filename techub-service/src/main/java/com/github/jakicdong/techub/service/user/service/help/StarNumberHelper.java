package com.github.jakicdong.techub.service.user.service.help;

import com.github.jakicdong.techub.service.user.service.conf.AiConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
* @author JakicDong
* @description 密码加密器，后续接入SpringSecurity之后，可以使用 PasswordEncoder 进行替换
* @time 2025/7/9 11:29
*/
@Component
public class StarNumberHelper {
    @Resource
    private AiConfig aiConfig;

    public Boolean checkStarNumber(String starNumber) {
        // 判断编号是否在 0 - maxStarNumber 之间
        return Integer.parseInt(starNumber) >= 0 && Integer.parseInt(starNumber) <= aiConfig.getMaxNum().getStarNumber();
    }

}

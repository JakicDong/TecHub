package com.github.jakicdong.techub.web.front.login.pwd;

import com.github.jakicdong.techub.api.model.vo.ResVo;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import com.github.jakicdong.techub.api.model.vo.user.UserPwdLoginReq;
import com.github.jakicdong.techub.core.util.SessionUtil;
import com.github.jakicdong.techub.service.user.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/*
* @author JakicDong
* @description 用户名 密码方式的登录/登出的入口
* @time 2025/7/8 14:25
*/
@RestController
@RequestMapping
public class LoginRestController {
    @Autowired
    private LoginService loginService;

    /*
    * @author JakicDong
    * @description 用户名和密码登录
    *  可以根据星球编号/用户名进行密码匹配
    * @time 2025/7/10 16:38
    */
    @PostMapping("/login/username")
    public ResVo<Boolean> login(@RequestParam(name = "username") String username,
                                @RequestParam(name = "password") String password,
                                HttpServletResponse response){
        String session = loginService.loginByUserPwd(username, password);// <==进入service层@Aizen
        if (StringUtils.isNotBlank(session)) {
            // cookie中写入用户登录信息，用于身份识别
            response.addCookie(SessionUtil.newCookie(LoginService.SESSION_KEY, session));
            return ResVo.ok(true);
        } else {
            return ResVo.fail(StatusEnum.LOGIN_FAILED_MIXED, "用户名和密码登录异常，请稍后重试");
        }
    }

    /**
     * 绑定星球账号
     */
    @PostMapping("/login/register")
    public ResVo<Boolean> register(UserPwdLoginReq loginReq,
                                   HttpServletResponse response) {
        String session = loginService.registerByUserPwd(loginReq);
        if (StringUtils.isNotBlank(session)) {
            // cookie中写入用户登录信息，用于身份识别
            response.addCookie(SessionUtil.newCookie(LoginService.SESSION_KEY, session));
            return ResVo.ok(true);
        } else {
            return ResVo.fail(StatusEnum.LOGIN_FAILED_MIXED, "用户名和密码登录异常，请稍后重试");
        }
    }


}

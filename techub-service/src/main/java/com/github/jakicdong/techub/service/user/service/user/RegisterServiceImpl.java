package com.github.jakicdong.techub.service.user.service.user;

import com.github.jakicdong.techub.service.user.repository.dao.UserAiDao;
import com.github.jakicdong.techub.service.user.repository.dao.UserDao;
import com.github.jakicdong.techub.service.user.service.RegisterService;
import com.github.jakicdong.techub.service.user.service.help.UserPwdEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* @author JakicDong
* @description 用户注册服务实现
* @time 2025/7/9 14:52
*/
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserPwdEncoder userPwdEncoder;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAiDao userAiDao;


}

package com.github.jakicdong.techub.service.user.service.user;

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.exception.ExceptionUtil;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import com.github.jakicdong.techub.api.model.vo.user.UserPwdLoginReq;
import com.github.jakicdong.techub.service.user.repository.dao.UserAiDao;
import com.github.jakicdong.techub.service.user.repository.dao.UserDao;
import com.github.jakicdong.techub.service.user.repository.entity.UserAiDO;
import com.github.jakicdong.techub.service.user.repository.entity.UserDO;
import com.github.jakicdong.techub.service.user.service.LoginService;
import com.github.jakicdong.techub.service.user.service.RegisterService;
import com.github.jakicdong.techub.service.user.service.UserAiService;
import com.github.jakicdong.techub.service.user.service.UserService;
import com.github.jakicdong.techub.service.user.service.help.StarNumberHelper;
import com.github.jakicdong.techub.service.user.service.help.UserPwdEncoder;
import com.github.jakicdong.techub.service.user.service.help.UserSessionHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
* @author JakicDong
* @description 基于验证码、用户名密码的登录方式
* @time 2025/7/9 11:09
*/

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAiDao userAiDao;

    @Autowired
    private UserSessionHelper userSessionHelper;

    @Autowired
    private StarNumberHelper starNumberHelper;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserPwdEncoder userPwdEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAiService userAiService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    /**
     * 给微信公众号的用户生成一个用于登录的会话
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public String loginByWx(Long userId) {
        return userSessionHelper.genSession(userId);
    }

    @Override
    public void logout(String session) {
        userSessionHelper.removeSession(session);
    }


    /*
    * 用户密码方式登录
    * */
    @Override
    public String loginByUserPwd(String username, String password) {
        UserDO user = userDao.getUserByUserName(username);
        if (user == null) {
            throw ExceptionUtil.of(StatusEnum.USER_NOT_EXISTS, "userName=" + username);
        }
        // passwordEncoder.matches(password, user.getPassword());
        if (!userPwdEncoder.match(password, user.getPassword())) {
            throw ExceptionUtil.of(StatusEnum.USER_PWD_ERROR);
        }

        Long userId = user.getId();
        // 1. 为了兼容历史数据，对于首次登录成功的用户，初始化ai信息
        userAiService.initOrUpdateAiInfo(new UserPwdLoginReq().setUserId(userId).setUsername(username).setPassword(password));

        // 登录成功，返回对应的session
        ReqInfoContext.getReqInfo().setUserId(userId);
        return userSessionHelper.genSession(userId);
    }

    /**
     * 用户名密码方式登录，若用户不存在，则进行注册
     *
     * @param loginReq 登录信息
     * @return
     */
    @Override
    public String registerByUserPwd(UserPwdLoginReq loginReq) {
        // 1. 前置校验
        registerPreCheck(loginReq);

        // 2. 判断当前用户是否登录，若已经登录，则直接走绑定流程
        Long userId = ReqInfoContext.getReqInfo().getUserId();
        loginReq.setUserId(userId);
        if (userId != null) {
            // 2.1 如果用户已经登录，则走绑定用户信息流程
            userService.bindUserInfo(loginReq);
            return ReqInfoContext.getReqInfo().getSession();
        }


        // 3. 尝试使用用户名进行登录，若成功，则依然走绑定流程
        UserDO user = userDao.getUserByUserName(loginReq.getUsername());
        if (user != null) {
            if (!userPwdEncoder.match(loginReq.getPassword(), user.getPassword())) {
                // 3.1 用户名已经存在
                throw ExceptionUtil.of(StatusEnum.USER_LOGIN_NAME_REPEAT, loginReq.getUsername());
            }

            // 3.2 用户存在，尝试走绑定流程
            userId = user.getId();
            loginReq.setUserId(userId);
            userAiService.initOrUpdateAiInfo(loginReq);
        } else {
            //4. 走用户注册流程
            userId = registerService.registerByUserNameAndPassword(loginReq);
        }
        ReqInfoContext.getReqInfo().setUserId(userId);
        return userSessionHelper.genSession(userId);
    }

    /**
     * 注册前置校验
     *
     * @param loginReq
     */
    private void registerPreCheck(UserPwdLoginReq loginReq) {
        //如果输入的用户名或密码为空，则直接抛异常
        if (StringUtils.isBlank(loginReq.getUsername()) || StringUtils.isBlank(loginReq.getPassword())) {
            throw ExceptionUtil.of(StatusEnum.USER_PWD_ERROR);
        }

        String starNumber = loginReq.getStarNumber();
        // 若传了星球信息，首先进行校验
        if (StringUtils.isNotBlank(starNumber)) {
            if (Boolean.FALSE.equals(starNumberHelper.checkStarNumber(starNumber))) {
                // 星球编号校验不通过，直接抛异常
                throw ExceptionUtil.of(StatusEnum.USER_STAR_NOT_EXISTS, "星球编号=" + starNumber);
            }

            UserAiDO userAi = userAiDao.getByStarNumber(starNumber);

            // 如果星球编号已经被绑定了
            if (userAi != null) {
                // 判断星球是否已经被绑定了
                throw ExceptionUtil.of(StatusEnum.USER_STAR_REPEAT, starNumber);
            }
        }

        String invitationCode = loginReq.getInvitationCode();
        if (StringUtils.isNotBlank(invitationCode) && userAiDao.getByInviteCode(invitationCode) == null) {
            // 填写的邀请码不对, 找不到对应的用户
            throw ExceptionUtil.of(StatusEnum.UNEXPECT_ERROR, "非法的邀请码【" + starNumber + "】");
        }
    }
}

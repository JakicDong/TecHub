package com.github.jakicdong.techub.service.user.service.user;

import com.github.jakicdong.techub.api.model.enums.NotifyTypeEnum;
import com.github.jakicdong.techub.api.model.enums.user.LoginTypeEnum;
import com.github.jakicdong.techub.api.model.exception.ExceptionUtil;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import com.github.jakicdong.techub.api.model.vo.notify.NotifyMsgEvent;
import com.github.jakicdong.techub.api.model.vo.user.UserPwdLoginReq;
import com.github.jakicdong.techub.core.util.RandUtil;
import com.github.jakicdong.techub.core.util.SpringUtil;
import com.github.jakicdong.techub.service.user.converter.UserAiConverter;
import com.github.jakicdong.techub.service.user.repository.dao.UserAiDao;
import com.github.jakicdong.techub.service.user.repository.dao.UserDao;
import com.github.jakicdong.techub.service.user.repository.entity.UserAiDO;
import com.github.jakicdong.techub.service.user.repository.entity.UserDO;
import com.github.jakicdong.techub.service.user.repository.entity.UserInfoDO;
import com.github.jakicdong.techub.service.user.service.RegisterService;
import com.github.jakicdong.techub.service.user.service.help.UserPwdEncoder;
import com.github.jakicdong.techub.service.user.service.help.UserRandomGenHelper;
import com.github.jakicdong.techub.core.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long registerSystemUser(String loginUser, String nickUser, String avatar) {
        UserDO dbUser = userDao.getUserByUserName(loginUser);
        if (dbUser != null) {
            return dbUser.getId();
        }

        // 注册系统账号
        UserDO user = new UserDO();
        user.setUserName(loginUser);
        user.setThirdAccountId("system_" + RandUtil.random(16));
        user.setLoginType(LoginTypeEnum.WECHAT.getType());
        userDao.saveUser(user);

        UserInfoDO userInfo = new UserInfoDO();
        userInfo.setUserId(user.getId());
        userInfo.setUserName(nickUser);
        userInfo.setPhoto(avatar);
        userDao.save(userInfo);
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long registerByUserNameAndPassword(UserPwdLoginReq loginReq) {
        // 1. 判断用户名是否准确
        UserDO user = userDao.getUserByUserName(loginReq.getUsername());
        if (user != null) {
            throw ExceptionUtil.of(StatusEnum.USER_LOGIN_NAME_REPEAT, loginReq.getUsername());
        }

        // 2. 保存用户登录信息
        user = new UserDO();
        user.setUserName(loginReq.getUsername());
        user.setPassword(userPwdEncoder.encPwd(loginReq.getPassword()));
        user.setThirdAccountId("");
        // 用户名密码注册
        user.setLoginType(LoginTypeEnum.USER_PWD.getType());
        userDao.saveUser(user);

        // 3. 保存用户信息
        UserInfoDO userInfo = new UserInfoDO();
        userInfo.setUserId(user.getId());
        userInfo.setUserName(loginReq.getUsername());
        userInfo.setPhoto(UserRandomGenHelper.genAvatar());
        userDao.save(userInfo);

        // 4. 保存ai相互信息
        UserAiDO userAiDO = UserAiConverter.initAi(user.getId(), loginReq.getStarNumber());
        userAiDao.saveOrUpdateAiBindInfo(userAiDO, loginReq.getInvitationCode());
        processAfterUserRegister(user.getId());
        return user.getId();
    }



    /**
     * 用户注册完毕之后触发的动作
     *
     * @param userId
     */
    private void processAfterUserRegister(Long userId) {
        TransactionUtil.registryAfterCommitOrImmediatelyRun(new Runnable() {
            @Override
            public void run() {
                // 用户注册事件
                SpringUtil.publishEvent(new NotifyMsgEvent<>(this, NotifyTypeEnum.REGISTER, userId));
            }
        });
    }
    
    /*
    * @author JakicDong
    * @description 通过微信公众号进行注册
    * @time 2025/7/31 15:59
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long registerByWechat(String thirdAccount){
        // 用户不存在，则需要注册
        // 1. 保存用户登录信息
        UserDO user = new UserDO();
        user.setThirdAccountId(thirdAccount);
        user.setLoginType(LoginTypeEnum.WECHAT.getType());
        userDao.saveUser(user);

        // 2. 初始化用户信息，随机生成用户昵称 + 头像
        UserInfoDO userInfo = new UserInfoDO();
        userInfo.setUserId(user.getId());
        userInfo.setUserName(UserRandomGenHelper.genNickName());
        userInfo.setPhoto(UserRandomGenHelper.genAvatar());
        userDao.save(userInfo);

        // 3. 保存ai相互信息
        UserAiDO userAiDO = UserAiConverter.initAi(user.getId());
        userAiDao.saveOrUpdateAiBindInfo(userAiDO, null);
        processAfterUserRegister(user.getId());
        return user.getId();
    }

}

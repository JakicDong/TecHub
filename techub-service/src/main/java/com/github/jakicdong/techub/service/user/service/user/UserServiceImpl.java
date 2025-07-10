package com.github.jakicdong.techub.service.user.service.user;

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.exception.ExceptionUtil;
import com.github.jakicdong.techub.api.model.vo.article.dto.YearArticleDTO;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import com.github.jakicdong.techub.api.model.vo.user.UserPwdLoginReq;
import com.github.jakicdong.techub.api.model.vo.user.dto.BaseUserInfoDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.SimpleUserInfoDTO;
import com.github.jakicdong.techub.api.model.vo.user.dto.UserStatisticInfoDTO;
import com.github.jakicdong.techub.core.util.IpUtil;
import com.github.jakicdong.techub.service.article.repository.dao.ArticleDao;
import com.github.jakicdong.techub.service.statistics.service.CountService;
import com.github.jakicdong.techub.service.user.converter.UserConverter;
import com.github.jakicdong.techub.service.user.repository.dao.UserAiDao;
import com.github.jakicdong.techub.service.user.repository.dao.UserDao;
import com.github.jakicdong.techub.service.user.repository.dao.UserRelationDao;
import com.github.jakicdong.techub.service.user.repository.entity.*;
import com.github.jakicdong.techub.service.user.service.UserAiService;
import com.github.jakicdong.techub.service.user.service.UserService;
import com.github.jakicdong.techub.service.user.service.help.UserPwdEncoder;
import com.github.jakicdong.techub.service.user.service.help.UserSessionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private UserAiDao userAiDao;

    @Autowired
    private UserSessionHelper userSessionHelper;
    @Resource
    private UserRelationDao userRelationDao;

    @Autowired
    private CountService countService;

    @Autowired
    private ArticleDao articleDao;

//    @Autowired
//    private UserSessionHelper userSessionHelper;
//
    @Autowired
    private UserPwdEncoder userPwdEncoder;

    @Autowired
    private UserAiService userAiService;

    @Override
    public List<SimpleUserInfoDTO> batchQuerySimpleUserInfo(Collection<Long> userIds) {
        List<UserInfoDO> users = userDao.getByUserIds(userIds);
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }
        return users.stream().map(UserConverter::toSimpleInfo).collect(Collectors.toList());
    }

    @Override
    public BaseUserInfoDTO queryBasicUserInfo(Long userId) {
        UserInfoDO user = userDao.getByUserId(userId);
        if (user == null) {
            throw ExceptionUtil.of(StatusEnum.USER_NOT_EXISTS, "userId=" + userId);
        }
        return UserConverter.toDTO(user);
    }

    @Override
    public UserStatisticInfoDTO queryUserInfoWithStatistic(Long userId) {
        BaseUserInfoDTO userInfoDTO = queryBasicUserInfo(userId);
        UserStatisticInfoDTO userHomeDTO = countService.queryUserStatisticInfo(userId);
        userHomeDTO = UserConverter.toUserHomeDTO(userHomeDTO, userInfoDTO);

        // 用户资料完整度
        int cnt = 0;
        if (StringUtils.isNotBlank(userHomeDTO.getCompany())) {
            ++cnt;
        }
        if (StringUtils.isNotBlank(userHomeDTO.getPosition())) {
            ++cnt;
        }
        if (StringUtils.isNotBlank(userHomeDTO.getProfile())) {
            ++cnt;
        }
        userHomeDTO.setInfoPercent(cnt * 100 / 3);

        // 是否关注
        Long followUserId = ReqInfoContext.getReqInfo().getUserId();
        if (followUserId != null) {
            UserRelationDO userRelationDO = userRelationDao.getUserRelationByUserId(userId, followUserId);
            userHomeDTO.setFollowed((userRelationDO == null) ? Boolean.FALSE : Boolean.TRUE);
        } else {
            userHomeDTO.setFollowed(Boolean.FALSE);
        }

        // 加入天数
        int joinDayCount = (int) ((System.currentTimeMillis() - userHomeDTO.getCreateTime()
                .getTime()) / (1000 * 3600 * 24));
        userHomeDTO.setJoinDayCount(Math.max(1, joinDayCount));

        // 创作历程
        List<YearArticleDTO> yearArticleDTOS = articleDao.listYearArticleByUserId(userId);
        userHomeDTO.setYearArticleList(yearArticleDTOS);
        return userHomeDTO;
    }
    
    @Override
    public BaseUserInfoDTO getAndUpdateUserIpInfoBySessionId(String session, String clientIp) {
        if (StringUtils.isBlank(session)) {
            return null;
        }

        Long userId = userSessionHelper.getUserIdBySession(session);
        if (userId == null) {
            return null;
        }

        // 查询用户信息，并更新最后一次使用的ip
        UserInfoDO user = userDao.getByUserId(userId);
        if (user == null) {
            // 常见于：session中记录的用户被删除了，直接移除缓存中的session，走重新登录流程
            userSessionHelper.removeSession(session);
            return null;
        }

        IpInfo ip = user.getIp();
        if (clientIp != null && !Objects.equals(ip.getLatestIp(), clientIp)) {
            // ip不同，需要更新
            ip.setLatestIp(clientIp);
            ip.setLatestRegion(IpUtil.getLocationByIp(clientIp).toRegionStr());

            if (ip.getFirstIp() == null) {
                ip.setFirstIp(clientIp);
                ip.setFirstRegion(ip.getLatestRegion());
            }
            userDao.updateById(user);
        }
        UserAiDO userAiDO = userAiDao.getByUserId(userId);
        return UserConverter.toDTO(user, userAiDO);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindUserInfo(UserPwdLoginReq loginReq) {
        // 0. 绑定用户名 & 密码 前置校验
        UserDO user = userDao.getUserByUserName(loginReq.getUsername());
        if (user == null) {
            // 用户名不存在，则标识当前登录用户可以使用这个用户名
            user = new UserDO();
            user.setId(loginReq.getUserId());
        } else if (!Objects.equals(loginReq.getUserId(), user.getId())) {
            // 登录用户名已经存在了
            throw ExceptionUtil.of(StatusEnum.USER_LOGIN_NAME_REPEAT, loginReq.getUsername());
        }

        // 1. 更新用户名密码
        user.setUserName(loginReq.getUsername());
        user.setPassword(userPwdEncoder.encPwd(loginReq.getPassword()));
        userDao.saveUser(user);

        // 2. 更新ai相关信息
        userAiService.initOrUpdateAiInfo(loginReq);
    }

}

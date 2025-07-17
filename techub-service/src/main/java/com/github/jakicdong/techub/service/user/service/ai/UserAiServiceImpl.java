package com.github.jakicdong.techub.service.user.service.ai;

import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.enums.user.UserAIStatEnum;
import com.github.jakicdong.techub.api.model.vo.user.UserPwdLoginReq;
import com.github.jakicdong.techub.service.user.converter.UserAiConverter;
import com.github.jakicdong.techub.service.user.repository.dao.UserAiDao;
import com.github.jakicdong.techub.service.user.repository.entity.UserAiDO;
import com.github.jakicdong.techub.service.user.service.UserAiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserAiServiceImpl implements UserAiService {

    @Autowired
    private UserAiDao userAiDao;

    @Override
    public void initOrUpdateAiInfo(UserPwdLoginReq loginReq) {
        // 之前已经检查过编号是否已经被绑定过了，那我们直接进行绑定
        Long userId = loginReq.getUserId();
        UserAiDO userAiDO = userAiDao.getByUserId(userId);
        if (userAiDO == null) {
            // 初始化新的ai信息
            userAiDO = UserAiConverter.initAi(userId, loginReq.getStarNumber());
        } else if (StringUtils.isBlank(loginReq.getStarNumber()) && StringUtils.isBlank(loginReq.getInvitationCode())) {
            // 没有传递星球和邀请码时，直接返回，不用更新ai信息
            return;
        } else if (StringUtils.isNotBlank(loginReq.getStarNumber())) {
            // 之前有绑定信息，检查到与之前的不一致，则执行更新星球编号流程
            if (!Objects.equals(loginReq.getStarNumber(), userAiDO.getStarNumber())) {
                userAiDO.setStarNumber(loginReq.getStarNumber());
            }
            // 并设置为试用
            userAiDO.setState(UserAIStatEnum.TRYING.getCode());
            if (ReqInfoContext.getReqInfo().getUser() != null) {
                ReqInfoContext.getReqInfo().getUser().setStarStatus(UserAIStatEnum.TRYING);
            }
        }
        userAiDao.saveOrUpdateAiBindInfo(userAiDO, loginReq.getInvitationCode());
    }
}

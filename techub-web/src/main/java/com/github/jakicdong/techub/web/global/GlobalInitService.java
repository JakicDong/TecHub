package com.github.jakicdong.techub.web.global;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.vo.seo.Seo;
import com.github.jakicdong.techub.api.model.vo.user.dto.BaseUserInfoDTO;
import com.github.jakicdong.techub.core.util.NumUtil;
import com.github.jakicdong.techub.core.util.SessionUtil;
import com.github.jakicdong.techub.service.notify.service.NotifyService;
import com.github.jakicdong.techub.service.sitemap.service.SitemapService;
import com.github.jakicdong.techub.service.statistics.service.UserStatisticService;
import com.github.jakicdong.techub.service.user.service.LoginService;
import com.github.jakicdong.techub.service.user.service.UserService;
import com.github.jakicdong.techub.web.config.GlobalViewConfig;
import com.github.jakicdong.techub.web.global.vo.GlobalVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;

/*
* @author JakicDong
* @description 全局初始化服务
* @time 2025/7/1 20:19
*/
@Slf4j
@Service
public class GlobalInitService {

    @Value("${env.name}")
    private String env;
    @Autowired
    private UserService userService;

    @Resource
    private GlobalViewConfig globalViewConfig;

    @Resource
    private NotifyService notifyService;

    @Resource
    private SeoInjectService seoInjectService;

    @Resource
    private UserStatisticService userStatisticService;

    @Resource
    private SitemapService sitemapService;

    /**
     * 初始化用户信息
     *
     * @param reqInfo
     */
    public void initLoginUser(ReqInfoContext.ReqInfo reqInfo) {
        //Holder Spring提供的工具类,持有当前请求的上下文信息
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (request.getCookies() == null) {
            return;
        }
        Optional.ofNullable(SessionUtil.findCookieByName(request, LoginService.SESSION_KEY))
                .ifPresent(cookie -> initLoginUser(cookie.getValue(), reqInfo));
    }


    public void initLoginUser(String session, ReqInfoContext.ReqInfo reqInfo) {
        BaseUserInfoDTO user = userService.getAndUpdateUserIpInfoBySessionId(session, null);
        if (user != null) {
            reqInfo.setSession(session);
            reqInfo.setUserId(user.getUserId());
            reqInfo.setUser(user);
            reqInfo.setMsgNum(notifyService.queryUserNotifyMsgCount(user.getUserId()));
        }
    }


    /**
     * 全局属性配置
     */
    public GlobalVo globalAttr() {
        GlobalVo vo = new GlobalVo();
        vo.setEnv(env);
        vo.setSiteInfo(globalViewConfig);
        vo.setOnlineCnt(userStatisticService.getOnlineUserCnt());
        vo.setSiteStatisticInfo(sitemapService.querySiteVisitInfo(null, null));
        vo.setTodaySiteStatisticInfo(sitemapService.querySiteVisitInfo(LocalDate.now(), null));

        //添加seo 判断seo中的ogp是否为空，为空则使用默认seo
        if (ReqInfoContext.getReqInfo() == null || ReqInfoContext.getReqInfo().getSeo() == null || CollectionUtils.isEmpty(ReqInfoContext.getReqInfo().getSeo().getOgp())) {

            Seo seo = seoInjectService.defaultSeo();
            vo.setOgp(seo.getOgp());
            vo.setJsonLd(JSONUtil.toJsonStr(seo.getJsonLd()));
        } else {
            Seo seo = ReqInfoContext.getReqInfo().getSeo();
            vo.setOgp(seo.getOgp());
            vo.setJsonLd(JSONUtil.toJsonStr(seo.getJsonLd()));
        }

        //检查登录状态并且设置相应信息到全局VO对象当中
        try {
            if (ReqInfoContext.getReqInfo() != null && NumUtil.upZero(ReqInfoContext.getReqInfo().getUserId())) {
                vo.setIsLogin(true);
                vo.setUser(ReqInfoContext.getReqInfo().getUser());
                vo.setMsgNum(ReqInfoContext.getReqInfo().getMsgNum());
            } else {
                vo.setIsLogin(false);
            }

            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            if (request.getRequestURI().startsWith("/column")) {
                vo.setCurrentDomain("column");
            } else if (request.getRequestURI().startsWith("/chat")) {
                vo.setCurrentDomain("chat");
            } else {
                vo.setCurrentDomain("article");
            }
        } catch (Exception e) {
            log.error("loginCheckError:", e);
        }
        return vo;
    }

}

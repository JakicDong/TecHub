package com.github.JakicDong.TecHub.web.global;


import com.github.JakicDong.TecHub.service.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author JakicDong
 * @date 2025.6.27
 * @description 全局初始化服务
 */
@Slf4j
@Service
public class GlobalInitService {
    @Value("${env.name}")
    private String env;
    @Autowired
    private UserService userService;

//    @Resource
//    private GlobalViewConfig globalViewConfig;
//
//    @Resource
//    private NotifyService notifyService;
//
//    @Resource
//    private SeoInjectService seoInjectService;
//
//    @Resource
//    private UserStatisticService userStatisticService;
//
//    @Resource
//    private SitemapService sitemapService;
//
//    /**
//     * 全局属性配置
//     */
//    public GlobalVo globalAttr() {
//        GlobalVo vo = new GlobalVo();
//        vo.setEnv(env);
//        vo.setSiteInfo(globalViewConfig);
//        vo.setOnlineCnt(userStatisticService.getOnlineUserCnt());
//        vo.setSiteStatisticInfo(sitemapService.querySiteVisitInfo(null, null));
//        vo.setTodaySiteStatisticInfo(sitemapService.querySiteVisitInfo(LocalDate.now(), null));
//
//        if (ReqInfoContext.getReqInfo() == null || ReqInfoContext.getReqInfo().getSeo() == null || CollectionUtils.isEmpty(ReqInfoContext.getReqInfo().getSeo().getOgp())) {
//            Seo seo = seoInjectService.defaultSeo();
//            vo.setOgp(seo.getOgp());
//            vo.setJsonLd(JSONUtil.toJsonStr(seo.getJsonLd()));
//        } else {
//            Seo seo = ReqInfoContext.getReqInfo().getSeo();
//            vo.setOgp(seo.getOgp());
//            vo.setJsonLd(JSONUtil.toJsonStr(seo.getJsonLd()));
//        }
//
//        try {
//            if (ReqInfoContext.getReqInfo() != null && NumUtil.upZero(ReqInfoContext.getReqInfo().getUserId())) {
//                vo.setIsLogin(true);
//                vo.setUser(ReqInfoContext.getReqInfo().getUser());
//                vo.setMsgNum(ReqInfoContext.getReqInfo().getMsgNum());
//            } else {
//                vo.setIsLogin(false);
//            }
//
//            HttpServletRequest request =
//                    ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//            if (request.getRequestURI().startsWith("/column")) {
//                vo.setCurrentDomain("column");
//            } else if (request.getRequestURI().startsWith("/chat")) {
//                vo.setCurrentDomain("chat");
//            } else {
//                vo.setCurrentDomain("article");
//            }
//        } catch (Exception e) {
//            log.error("loginCheckError:", e);
//        }
//        return vo;
//    }
//
//    /**
//     * 初始化用户信息
//     *
//     * @param reqInfo
//     */
//    public void initLoginUser(ReqInfoContext.ReqInfo reqInfo) {
//        HttpServletRequest request =
//                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        if (request.getCookies() == null) {
//            Optional.ofNullable(request.getHeader("Authorization"))
//                    .ifPresent(cookie -> initLoginUser(request.getHeader("Authorization"), reqInfo));
//            return;
//        }
////        Optional.ofNullable(request.getHeader("Authorization"))
////                .ifPresent(token -> initLoginUser(token, reqInfo));
////        if(request.getHeader("Authorization") != null){
////            String token = request.getHeader("Authorization");
////            initLoginUser(token, reqInfo);
////        }
//        Optional.ofNullable(SessionUtil.findCookieByName(request, LoginService.SESSION_KEY))
//                .ifPresent(cookie -> initLoginUser(cookie.getValue(), reqInfo));
//    }
//
//    public void initLoginUser(String session, ReqInfoContext.ReqInfo reqInfo) {
//        BaseUserInfoDTO user = userService.getAndUpdateUserIpInfoBySessionId(session, null);
//        reqInfo.setSession(session);
//        if (user != null) {
//            reqInfo.setUserId(user.getUserId());
//            reqInfo.setUser(user);
//            reqInfo.setMsgNum(notifyService.queryUserNotifyMsgCount(user.getUserId()));
//        }
//    }
}
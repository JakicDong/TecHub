package com.github.jakicdong.techub.web.hook.interceptor;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.github.hui.quick.plugin.qrcode.util.json.JsonUtil;
import com.github.jakicdong.techub.api.model.context.ReqInfoContext;
import com.github.jakicdong.techub.api.model.vo.ResVo;
import com.github.jakicdong.techub.api.model.vo.constants.StatusEnum;
import com.github.jakicdong.techub.core.permission.Permission;
import com.github.jakicdong.techub.core.permission.UserRole;
import com.github.jakicdong.techub.core.util.SpringUtil;
import com.github.jakicdong.techub.service.rank.service.UserActivityRankService;
import com.github.jakicdong.techub.service.rank.service.model.ActivityScoreBo;
import com.github.jakicdong.techub.web.global.GlobalInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* @author JakicDong
* @description 全局拦截器，用于拦截所有的请求.
* thymleaf 站点信息，基本信息，在这里注入
* @time 2025/7/5 15:51
*/
@Slf4j
@Component
public class GlobalViewInterceptor implements AsyncHandlerInterceptor {
    //AsyncHandlerInterceptor 是一个异步的拦截器
    @Autowired
    private GlobalInitService globalInitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前请求是不是映射到了具体的Controller方法上
        if (handler instanceof HandlerMethod) {
            //打印请求路径
            log.info(">>>>>PRE  GlobalViewInterceptor:preHandle开始====请求路径：{}<<<<<", request.getRequestURI());

            //权限注解获取
            HandlerMethod handlerMethod = (HandlerMethod) handler;
           //先看方法上有没有权限注解，没有再看类上有没有权限注解
            Permission permission = handlerMethod.getMethod().getAnnotation(Permission.class);
            if (permission == null) {
                permission = handlerMethod.getBeanType().getAnnotation(Permission.class);
            }
            //无权限时候的处理
            if (permission == null || permission.role() == UserRole.ALL) {
                if (ReqInfoContext.getReqInfo() != null) {
                    // 用户活跃度更新
                    SpringUtil.getBean(UserActivityRankService.class).addActivityScore(ReqInfoContext.getReqInfo().getUserId(), new ActivityScoreBo().setPath(ReqInfoContext.getReqInfo().getPath()));
                }
                return true;
            }

            if (ReqInfoContext.getReqInfo() == null || ReqInfoContext.getReqInfo().getUserId() == null) {
                if (handlerMethod.getMethod().getAnnotation(ResponseBody.class) != null
                        || handlerMethod.getMethod().getDeclaringClass().getAnnotation(RestController.class) != null) {
                    // 访问需要登录的rest接口
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.getWriter().println(JsonUtil.toStr(ResVo.fail(StatusEnum.FORBID_NOTLOGIN)));
                    response.getWriter().flush();
                    return false;
                } else if (request.getRequestURI().startsWith("/api/admin/") || request.getRequestURI().startsWith("/admin/")) {
                    response.sendRedirect("/admin");
                } else {
                    // 访问需要登录的页面时，直接跳转到登录界面
                    response.sendRedirect("/");
                }
                return false;
            }
            if (permission.role() == UserRole.ADMIN && !UserRole.ADMIN.name().equalsIgnoreCase(ReqInfoContext.getReqInfo().getUser().getRole())) {
                // 设置为无权限
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }
        }
        return true;
    }

    //在请求处理之,渲染视图之前执行
    /*
     * 参数说明：
     *  1. request：当前请求对象
     *  2. response：当前响应对象
     *  3. handler：当前请求的处理器方法对象
     *  4. modelAndView：封装了视图和模型数据的对象
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(">>>>POST GlobalViewInterceptor:postHandle====请求路径：{}", request.getRequestURI());
        if (!ObjectUtils.isEmpty(modelAndView)) {
            log.info(">>>>POST modelAndView不为空");
            if (response.getStatus() != HttpStatus.OK.value()) {
                try {
                    ReqInfoContext.ReqInfo reqInfo = new ReqInfoContext.ReqInfo();
                    // fixme 对于异常重定向到 /error 时，会导致登录信息丢失，待解决
                    globalInitService.initLoginUser(reqInfo);
                    ReqInfoContext.addReqInfo(reqInfo);
                    //添加信息到全局视图当中
                    modelAndView.getModel().put("global", globalInitService.globalAttr());
                } finally {
                    ReqInfoContext.clear();
                }
            } else {
                modelAndView.getModel().put("global", globalInitService.globalAttr());
            }

        }

    }

}

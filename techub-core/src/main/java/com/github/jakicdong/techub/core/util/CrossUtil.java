package com.github.jakicdong.techub.core.util;


/*
* @author JakicDong
* @description 跨域支持工具类
* @time 2025/7/12 10:54
*/

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrossUtil {
    /*
    * @author JakicDong
    * @description 支持跨域
    * @time 2025/7/12 11:08
    */
    public static void buildCors(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求头中的 Origin 字段，该字段表示请求发起的源（协议 + 域名 + 端口）
        String origin = request.getHeader("Origin");
        // 判断 Origin 字段是否为空或仅包含空白字符
        if (StringUtils.isBlank(origin)) {
            // 若 Origin 为空，设置允许所有源访问，使用通配符 *
            response.setHeader("Access-Control-Allow-Origin", "*");
            // 当使用通配符 * 时，不允许携带凭证（如 Cookie、HTTP 认证等）
            response.setHeader("Access-Control-Allow-Credentials", "false");
        } else {
            // 若 Origin 不为空，仅允许当前请求的源访问
            response.setHeader("Access-Control-Allow-Origin", origin);
            // 允许携带凭证（如 Cookie、HTTP 认证等）
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        // 设置允许的 HTTP 请求方法，客户端可以使用这些方法发起跨域请求
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        // 设置预检请求的缓存时间（单位：秒），在该时间内，相同的预检请求将不再发送
        response.setHeader("Access-Control-Max-Age", "3600");
        // 设置允许的请求头，客户端在请求中可以携带这些头信息
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Real-IP, X-Forwarded-For, d-uuid, User-Agent, x-zd-cs, Proxy-Client-IP, HTTP_CLIENT_IP, HTTP_X_FORWARDED_FOR");
    }
}

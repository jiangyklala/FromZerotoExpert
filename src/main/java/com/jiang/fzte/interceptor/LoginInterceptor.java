package com.jiang.fzte.interceptor;

import com.jiang.fzte.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getCookies() != null) {
            try {
                Jedis jedis= userService.jedisPool.getResource();
                Cookie fzteUser = WebUtils.getCookie(request, "fzteUser");
                Cookie onlyLoginCert = WebUtils.getCookie(request, "loginCert");
//                String onlyLoginCert = request.getHeader("token");
                System.out.println(onlyLoginCert);
                if (fzteUser != null && onlyLoginCert != null && Objects.equals(onlyLoginCert.getValue(), jedis.hget("fU:" + fzteUser.getValue(), "lc"))) {
                    return true;
                }
                jedis.close();
            } catch (Exception e) {
                return false;
            }
        }
        response.sendRedirect("/Login");
        return false;
    }
}

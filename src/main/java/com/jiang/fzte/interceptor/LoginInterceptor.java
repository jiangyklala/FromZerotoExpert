package com.jiang.fzte.interceptor;

import com.jiang.fzte.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

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
            Cookie fzteUser = WebUtils.getCookie(request, "fzteUser");
            Cookie loginCert = WebUtils.getCookie(request, "loginCert");
            if (fzteUser != null && loginCert != null && Objects.equals(loginCert.getValue(), userService.jedis.hget("fU:" + fzteUser.getValue(), "lc"))) {
                return true;
            }
        }
        response.sendRedirect("/Login");
        return false;
    }
}

package com.jiang.fzte.interceptor;

import com.jiang.fzte.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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
            String userName = "";
            String onlyLoginCert = "";
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "fzteUser")) userName = cookie.getValue();
                if (Objects.equals(cookie.getName(), "loginCert")) onlyLoginCert = cookie.getValue();
            }
            if (!Objects.equals(userName, "") && !Objects.equals(onlyLoginCert, "") && Objects.equals(onlyLoginCert, userService.jedis.hget(userName, "lc"))) {
                return true;
            }
//            if (userName == "" || onlyLoginCert == "" || !Objects.equals(onlyLoginCert, userService.jedis.hget(userName, "lc"))) {
//                response.sendRedirect("/Login");
//                return false;
//            }
        }
        response.sendRedirect("/Login");
        return false;
    }
}

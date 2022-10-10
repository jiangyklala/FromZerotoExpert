package com.jiang.fzte.interceptor;

import com.jiang.fzte.service.UserService;
import com.jiang.fzte.util.IpUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date()); // 只要年月日
        Cookie fzteUser = WebUtils.getCookie(request, "fzteUser");
        Jedis jedis = null;

        try {
            jedis = userService.jedisPool.getResource();

            // 验证登录凭证
            if (request.getCookies() != null && isValidLoginCert(jedis, request, fzteUser)) {

                // 记录PV
                jedis.incr("fU:pv:" + nowTime);
                // 记录IP
                String ip = IpUtils.getIpAddr(request);
                jedis.pfadd("fU:ip:" + nowTime, ip);
                // 记录UV
                if (fzteUser != null) jedis.pfadd("fU:uv:" + nowTime, fzteUser.getValue());

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        response.sendRedirect("/Login");
        return false;
    }

    boolean isValidLoginCert(Jedis jedis, HttpServletRequest request, Cookie fzteUser) {
        Cookie onlyLoginCert = WebUtils.getCookie(request, "loginCert");
        if (fzteUser != null && onlyLoginCert != null && Objects.equals(onlyLoginCert.getValue(), jedis.hget("fU:" + fzteUser.getValue(), "lc"))) {
            return true;
        }
        return false;
    }

}

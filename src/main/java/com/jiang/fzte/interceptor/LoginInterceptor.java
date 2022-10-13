package com.jiang.fzte.interceptor;

import com.jiang.fzte.annotation.VisitLimit;
import com.jiang.fzte.service.UserService;
import com.jiang.fzte.util.IpUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
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

        try (Jedis jedis = userService.jedisPool.getResource()) {

            // 记录PV
            jedis.incr("fU:pv:" + nowTime);
            // 记录IP
            String ip = IpUtils.getIpAddr(request);
            jedis.pfadd("fU:ip:" + nowTime, ip);
            // 记录UV
            if (fzteUser != null) jedis.pfadd("fU:uv:" + nowTime, fzteUser.getValue());

            // 验证接口访问限制
            if (isVisitLimit(request, response, handler, jedis)) return false;

            // 验证登录凭证
            if (request.getCookies() != null && isValidLoginCert(jedis, request, fzteUser)) return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/Login");
        return false;
    }

    /**
     * 接口访问限制
     */
    private boolean isVisitLimit(HttpServletRequest request, HttpServletResponse response, Object handler, Jedis jedis) throws IOException {
        String userIPAndURL = IpUtils.getIpAddr(request) + request.getRequestURI();

        if (handler instanceof HandlerMethod) {

            // 判断用户IP在白名单中, 直接返回
            if (jedis.sismember("fU:wI", userIPAndURL)) {
                return false;
            }

            // 不再名单中则进行接口限制判断
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 访问的接口中是否含有[访问次数限制]的注解
            if (method.isAnnotationPresent(VisitLimit.class)) {
                VisitLimit accessLimit = method.getAnnotation(VisitLimit.class);
                int limit = accessLimit.limit();
                long sec = accessLimit.sec();
                Integer value = null;
                if (jedis.exists(userIPAndURL)) {  // 用户在 sec 期间已经访问过吗
                    value = Integer.valueOf(String.valueOf(jedis.get(userIPAndURL)));
                    if (value < limit) {  // 判断访问次数是否超限
                        jedis.setex(userIPAndURL, sec, String.valueOf(value + 1));
                    } else {
                        // 请求太繁忙
                        response.getWriter().write("Requests are busy!");
                        return true;
                    }
                } else {
                    jedis.setex(userIPAndURL, sec, "1");
                }
            }
        }
        return false;
    }

    private boolean isValidLoginCert(Jedis jedis, HttpServletRequest request, Cookie fzteUser) {
        Cookie onlyLoginCert = WebUtils.getCookie(request, "loginCert");
        if (fzteUser != null && onlyLoginCert != null && Objects.equals(onlyLoginCert.getValue(), jedis.hget("fU:" + fzteUser.getValue(), "lc"))) {
            return true;
        }
        return false;
    }

}

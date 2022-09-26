package com.jiang.fzte.controller;

import com.jiang.fzte.domain.User;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册接口
     */
    @PostMapping("/Register")
    @ResponseBody
    public CommonResp<User> register(User user) {
        CommonResp<User> resp = new CommonResp<>();
        userService.isRegisterUserName(user.getUsername(), resp);
        userService.isRegisterPassword(user.getPassword(), resp);
        if (resp.isSuccess()) {
            userService.encryptPassword(user, userService.setSalt(user));  // 设置盐值并密码加密
            userService.addUser(user, resp);

        }
        return resp;
    }

    /**
     * 登录接口
     */
    @PostMapping("/Login")
    @ResponseBody
    public CommonResp<User> login(@CookieValue(value = "fzteUser", required = false) String sessionId, User user,  HttpServletRequest request, HttpServletResponse response) {
        CommonResp<User> resp = new CommonResp<>();
        userService.isLoginUserName(user.getUsername(), resp);
        userService.isLoginPassword(user, resp);  // 这里需要传入user, 获取其用户名和密码
        if (resp.isSuccess()) {
            // 登陆成功, 添加或者更新登录凭证
            if (sessionId != null && !userService.updateLoginCert(sessionId, response)) {
                // 在无Cookie记录, 或者更新登录凭证失败时, 重现添加新的登录凭证
                userService.addLoginCert(user.getUsername(), response);
            }
        }
        return resp;
    }

    @GetMapping("/welcome")
    @ResponseBody
    public CommonResp<String> welcome(@CookieValue(value = "fzteUser", required = false) String sessionId) {
        CommonResp<String> resp = new CommonResp<>();
        if (sessionId == null || Objects.equals(userService.jedis.get("u:" + sessionId + ":uN"), null)) {
            resp.setSuccess(false);
        }
        resp.setContent(userService.jedis.get("u:" + sessionId + ":uN"));
        return resp;
    }

    /**
     * 首页
     */
    @GetMapping("/FromZerotoExpert")
    public String fromZerotoExpert() {
        return "FromZerotoExpert";
    }

    /**
     * 注册
     */
    @GetMapping("/Register")
    public String register() {
        return "register";
    }

    /**
     * 登录
     */
    @GetMapping("/Login")
    public String login() {
        return "login";
    }
}

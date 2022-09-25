package com.jiang.fzte.controller;

import com.jiang.fzte.domain.User;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("user")
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
    public String login(User user) {
        CommonResp<User> resp = new CommonResp<>();
        userService.isLoginUserName(user.getUsername(), resp);
        userService.isLoginPassword(user, resp);  // 这里需要传入user, 获取其用户名和密码
        if (resp.isSuccess()) {
            return "true";
        } else {
            return resp.getMessage();
        }
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

package com.jiang.fzte.controller;

import com.jiang.fzte.domain.User;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
        userService.isRegisterUserAccount(user.getUseraccount(), resp);
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
    public CommonResp<User> login(@CookieValue(value = "fzteUser", required = false) String userAccount, User user, HttpServletResponse response) {
        CommonResp<User> resp = new CommonResp<>();
        userService.isLoginUserAccount(user.getUseraccount(), resp);
        userService.isLoginPassword(user, resp);  // 这里需要传入user, 获取其账号和密码
        if (resp.isSuccess()) {
            // 登陆成功, 添加或者更新登录凭证
            if (userAccount == null || !userService.updateLoginCert(userAccount, user.getUseraccount(), response)) {
                // 在无Cookie记录, 或者有Cookie但更新失败时, 重新添加新的登录凭证;(失败包括:sessionId失效和此时登录的账号和redis中记录的不同)
                userService.addLoginCert(user.getUseraccount(), response);
            }
            userService.userOnline(user.getUseraccount());
        }
        return resp;
    }

    /**
     * 自动登录接口
     */
    @GetMapping("/Welcome")
    @ResponseBody
    public CommonResp<String> welcome(@CookieValue(value = "fzteUser", required = false) String userAccount) {
        CommonResp<String> resp = new CommonResp<>();
        // 根据是否有Cookie, 且 Cookie 中的 userAccount 在 redis 中是否过期(防止用户手动更改 Cookie 有效期)
        if (userAccount == null || !userService.jedis.exists(userAccount)) {
            resp.setSuccess(false);
        } else {
            userService.userOnline(userAccount);
            resp.setContent(userAccount);
        }
        return resp;
    }

    /**
     * 用户离线设置
     */
    @PostMapping("/UserOffline")
    @ResponseBody
    public CommonResp userOffline(@CookieValue(value = "fzteUser", required = false) String userAccount) {
        CommonResp<String> resp = new CommonResp<>();
        // 极端情况: 用户在网站上登录时长超过一天, Cookie失效
        if (userAccount == null) {
            resp.setSuccess(false);
            resp.setMessage("找不到Cookie啊");
            return resp;
        }
        userService.userOffline(userAccount);
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

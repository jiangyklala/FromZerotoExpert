package com.jiang.fzte.controller;

import com.alibaba.fastjson.JSON;
import com.jiang.fzte.domain.User;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 显示当前在线人数及其账号
     * @return
     */
    @GetMapping("/ShowOnlineUsers")
    @ResponseBody
    public String showOnlineUsers() {
        String jsonString = "";
        try(Jedis jedis = userService.jedisPool.getResource()) {
            long currentTimeMillis = System.currentTimeMillis();
            // 查出15秒内发送心跳信息的用户
            jsonString = JSON.toJSONString(jedis.zrangeByScore("fU:oL", currentTimeMillis - 6 * 1000, currentTimeMillis));
//        System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    /**
     * 发送"心跳信息"
     */
    @PostMapping("/StillAlive")
    @ResponseBody
    public void stillAlive(HttpServletRequest request) {
        Cookie userAccount = WebUtils.getCookie(request, "fzteUser");
        if (userAccount != null) {
            userService.userOnline(userAccount.getValue(), System.currentTimeMillis());
        }
    }

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
        Jedis jedis= userService.jedisPool.getResource();
        // 根据是否有Cookie, 且 Cookie 中的 userAccount 在 redis 中是否过期(防止用户手动更改 Cookie 有效期)
        if (userAccount == null || !jedis.exists("fU:" + userAccount)) {
            resp.setSuccess(false);
        } else {
            resp.setContent(userAccount);
        }
        jedis.close();
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

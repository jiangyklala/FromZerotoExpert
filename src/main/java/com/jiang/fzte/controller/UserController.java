package com.jiang.fzte.controller;

import com.jiang.fzte.domain.User;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/seeALL")
    @ResponseBody
    public List<User> all() {
        return userService.all();
    }

    @GetMapping("/FromZerotoExpert")
    public String FromZerotoExpert() {
        return "FromZerotoExpert";
    }

    /**
     * 注册接口
     */
    @PostMapping("/register")
    @ResponseBody
    public CommonResp register(String username, String password) {
        CommonResp<User> resp = new CommonResp<>();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.addUser(user);
        resp.setContent(user);
        return resp;
    }
}

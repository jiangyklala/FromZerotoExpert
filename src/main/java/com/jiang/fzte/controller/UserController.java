package com.jiang.fzte.controller;

import com.jiang.fzte.domain.User;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.service.UserService;
import com.jiang.fzte.util.PasswordLimit;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
    public CommonResp register(@Validated User user) {
        CommonResp<User> resp = new CommonResp<>();
        userService.isUserName(user.getUsername(), resp);
        userService.isPassword(user.getPassword(), resp);
        if (resp.isSuccess()) {
            userService.addUser(user, resp);
        }
        return resp;
    }
}

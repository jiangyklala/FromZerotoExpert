package com.jiang.fzte.controller;

import com.jiang.fzte.domain.User;
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

    @GetMapping("/testList")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/FromZerotoExpert")
    public String FromZerotoExpert() {
        return "FromZerotoExpert";
    }

    @PostMapping("/register")
    public String register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.add(user);
        return "FromZerotoExpert";
    }
}

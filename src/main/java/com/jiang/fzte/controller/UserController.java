package com.jiang.fzte.controller;

import com.jiang.fzte.domain.User;
import com.jiang.fzte.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user/list")
    public List<User> list() {
        return userService.list();
    }
}

package com.jiang.fzte.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/FromZerotoExpert")
    public String hello() {
        return "嗨，欢迎您来到 from zero to expert";
    }
}

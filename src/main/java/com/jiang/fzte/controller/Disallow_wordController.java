package com.jiang.fzte.controller;

import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.service.Disallow_wordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/disallowWord")
public class Disallow_wordController {

    @Resource
    Disallow_wordService disallow_wordService;

    @PostMapping("/alter")
    public CommonResp alter() {
        return null;
    }
}

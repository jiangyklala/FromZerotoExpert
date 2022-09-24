package com.jiang.fzte.controller;

import com.jiang.fzte.domain.Disallow_word;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.service.Disallow_wordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Objects;

@Controller
@RequestMapping("/disallowWord")
public class Disallow_wordController {

    @Resource
    Disallow_wordService disallow_wordService;

    @PostMapping("/add")
    @ResponseBody
    public CommonResp<Disallow_word> add(Disallow_word disallow_word) {
        CommonResp<Disallow_word> commonResp = new CommonResp<>();
        disallow_wordService.add(disallow_word.getValue(), commonResp);
        return commonResp;
    }

    @PostMapping("/delete")
    @ResponseBody
    public CommonResp<Disallow_word> delete(Disallow_word disallow_word) {
        CommonResp<Disallow_word> commonResp = new CommonResp<>();
        disallow_wordService.delete(disallow_word.getValue(), commonResp);
        return commonResp;
    }
}

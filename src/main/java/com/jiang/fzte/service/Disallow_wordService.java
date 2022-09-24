package com.jiang.fzte.service;

import com.jiang.fzte.domain.Disallow_word;
import com.jiang.fzte.mapper.Disallow_wordMapper;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.util.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class Disallow_wordService {

    @Resource
    private Disallow_wordMapper disallow_wordMapper;

    public static Trie root;  // 项目中的唯一Trie

    @PostConstruct
    public void init() {
        List<String> impolitePhrases = selectValue();
        root = new Trie();
        for (String s : impolitePhrases) {
            root.insert(s, root);
        }
    }

    public void add(String value, CommonResp<Disallow_word> commonResp) {
        if (Objects.equals(value, "")) {
            commonResp.setSuccess(false);
            commonResp.setMessage(commonResp.getMessage() + "填入敏感词为空");
        }
        Disallow_wordService.root.insert(value, Disallow_wordService.root);
    }

    public void delete(String value, CommonResp<Disallow_word> commonResp) {
        if (Objects.equals(value, "")) {
            commonResp.setSuccess(false);
            commonResp.setMessage(commonResp.getMessage() + "填入敏感词为空");
        }
        Disallow_wordService.root.delete(value, Disallow_wordService.root);
    }



    public List<String> selectValue() {
        List<Disallow_word> disallow_wordList = disallow_wordMapper.selectByExample(null);
        List<String> impolitePhrases = new ArrayList<>();
        for (Disallow_word disallow_word : disallow_wordList) {
            impolitePhrases.add(disallow_word.getValue());
        }
        return  impolitePhrases;
    }
}

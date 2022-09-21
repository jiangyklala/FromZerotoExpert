package com.jiang.fzte.service;

import com.jiang.fzte.domain.Disallow_word;
import com.jiang.fzte.mapper.Disallow_wordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class Disallow_wordService {

    @Resource
    private Disallow_wordMapper disallow_wordMapper;

    public List<String> all() {
        List<Disallow_word> disallow_wordList = disallow_wordMapper.selectByExample(null);
        List<String> impolitePhrases = new ArrayList<>();
        for (Disallow_word disallow_word : disallow_wordList) {
            impolitePhrases.add(disallow_word.getValue());
        }
        return  impolitePhrases;
    }
}

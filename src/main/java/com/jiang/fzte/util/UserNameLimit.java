package com.jiang.fzte.util;

import com.jiang.fzte.mapper.Disallow_wordMapper;
import com.jiang.fzte.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserNameLimit {

    /**
     * 昵称限制
     * @param userName
     * @return 0 - 昵称符合; 1 - 含有敏感词
     */
    public static int userNameLimit(String userName, List<String> impolitePhrases) {


        // 敏感词判断
        Trie root = new Trie();
        for (String s : impolitePhrases) {
            root.insert(s);
        }
        if (root.check(userName, root)) {  // 传入敏感词Trie
            return 1;
        }
        return 0;
    }

}

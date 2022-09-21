package com.jiang.fzte.util;

import com.jiang.fzte.domain.User;
import com.jiang.fzte.domain.UserExample;
import com.jiang.fzte.mapper.Disallow_wordMapper;
import com.jiang.fzte.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserNameLimit {

    /**
     * 敏感词限制
     * @param userName
     * @return 0 - 昵称符合; 1 - 含有敏感词
     */
    public static int userNamePolite(String userName, List<String> impolitePhrases) {

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

    /**
     * 用户名唯一限制
     * @param userName
     * @param userExample
     * @param userMapper
     * @return
     */
    public static User userNameOnly(String userName, UserExample userExample, UserMapper userMapper) {
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(userName);
        List<User> userList = userMapper.selectByExample(userExample);  // 此处虽然只有一条也只能用List<User>接受
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }

}

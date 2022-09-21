package com.jiang.fzte.service;

import com.jiang.fzte.domain.User;
import com.jiang.fzte.domain.UserExample;
import com.jiang.fzte.mapper.Disallow_wordMapper;
import com.jiang.fzte.mapper.UserMapper;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.util.PasswordLimit;
import com.jiang.fzte.util.Trie;
import com.jiang.fzte.util.UserNameLimit;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private Disallow_wordService disallow_wordService;

    private Trie root;

    @PostConstruct
    public void init() {
        List<String> impolitePhrases = disallow_wordService.all();
        root = new Trie();
        for (String s : impolitePhrases) {
            root.insert(s);
        }
    }

    public List<User> all() {
        return userMapper.selectByExample(null);
    }

    /**
     * 用户名是否符合限制
     * @param userName
     * @param resp 传入最终返回结果类的引用, 进行修改
     */
    public void isUserName(String userName, CommonResp resp) {

        // 判断用户名唯一
        UserExample userExample = new UserExample();
        if (null != UserNameLimit.userNameOnly(userName, userExample, userMapper)) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "用户名重复; ");
        }
        // 判断敏感词
        switch (UserNameLimit.userNamePolite(userName, root)) {
            case 1 -> {
                resp.setSuccess(false);
                resp.setMessage(resp.getMessage() + "含有敏感词; ");  // 原有的信息也不要丢
            }
//            case 0 -> {
//                if (resp.isSuccess()) {  // 是否已经是 false 了??
//                    resp.setMessage(resp.getMessage() + "昵称符合限制; ");
//                }
//            }
        }
    }

    /**
     *
     * @param password
     * @param resp 传入最终返回结果类的引用, 进行修改
     */
    public void isPassword(String password, CommonResp resp) {
        switch (PasswordLimit.passWordLimit(password)) {
            case 3 -> {
                resp.setSuccess(false);
                resp.setMessage(resp.getMessage() + "密码需要至少两种字符; ");
            }
            case 2 -> {
                resp.setSuccess(false);
                resp.setMessage(resp.getMessage() + "密码只能包含数字和英文大小写三种字符; ");
            }
            case 1 -> {
                resp.setSuccess(false);
                resp.setMessage(resp.getMessage() + "密码长度只能在 6 - 16 位; ");
            }
//            case 0 -> {
//                if (resp.isSuccess()) {
//                    resp.setMessage(resp.getMessage() + "密码符合限制; ");
//                }
//            }
        }
    }

    public void addUser(User user, CommonResp<User> resp) {
        if (userMapper.insert(user) != 0) {
            resp.setMessage(resp.getMessage() + "用户添加成功");
            resp.setContent(user);
        } else {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "用户插入失败");
        }
    }
}

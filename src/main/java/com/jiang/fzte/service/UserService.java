package com.jiang.fzte.service;

import com.jiang.fzte.domain.User;
import com.jiang.fzte.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public List<User> all() {
        return userMapper.selectByExample(null);
    }

    public void addUser(User user) {
        userMapper.insert(user);
    }
}

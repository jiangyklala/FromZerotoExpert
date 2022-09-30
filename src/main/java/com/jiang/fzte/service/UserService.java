package com.jiang.fzte.service;

import com.jiang.fzte.domain.User;
import com.jiang.fzte.domain.UserExample;
import com.jiang.fzte.mapper.UserMapper;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.util.Md5Encrypt;
import com.jiang.fzte.util.PasswordLimit;
import com.jiang.fzte.util.SnowFlakeIdWorker;
import com.jiang.fzte.util.UserNameLimit;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private Disallow_wordService disallow_wordService;

    public Jedis jedis;


    @PostConstruct
    public void init() {
        jedis = new Jedis("124.223.184.187", 6379);
        jedis.auth("jiang");
        jedis.select(1);
    }

    @PreDestroy
    public void end() {
        jedis.close();
    }

    /**
     * 设置用户在线状态
     */
    public void userOnline(String sessionId) {
        jedis.hset(sessionId, "online", "1");
    }

    /**
     * 设置用户离线状态
     */
    public void userOffline(String userName) {
        jedis.hset(userName, "online", "0");
    }

    /**
     * 更新用户登录凭证
     */
    public boolean updateLoginCert(String userName, String nowUserName, HttpServletResponse response) {
        // 更新 userName
        if (jedis.expire(userName, 60 * 60 * 24) == 0) {
            // userName失效, 即找不到这个key
            return false;
        }

        //  此时登录的用户名和redis中记录的不同
        if (!Objects.equals(userName, nowUserName)) {
            // 删除原来的用户记录, 减少服务器空间消耗
            jedis.expire(userName, 0);
            return false;
        }

        // 更新唯一登录凭证
        String onlyLoginCert = Long.toString(System.currentTimeMillis());
        jedis.hset(userName, "lc", onlyLoginCert);  // lc : loginCert

        // 更新 cookieUserName
        Cookie cookieUserName = new Cookie("fzteUser", userName);
        cookieUserName.setMaxAge(60 * 60 * 24);
        response.addCookie(cookieUserName);
        // 更新 cookieLoginCert
        Cookie cookieLoginCert = new Cookie("loginCert", onlyLoginCert);
        cookieLoginCert.setMaxAge(60 * 60 * 24);
        response.addCookie(cookieLoginCert);
        return true;
    }

    /**
     * 添加用户登录凭证
     * @return 新生成的 sessionId
     */
    public void addLoginCert(String nowUserName, HttpServletResponse response) {

        // fzteUser = 用户名 创建Cookie
        Cookie cookieUserName = new Cookie("fzteUser", nowUserName);
        cookieUserName.setMaxAge(60 * 60 * 24);
        response.addCookie(cookieUserName);

        // loginCert = 唯一登录凭证 创建Cookie
        String onlyLoginCert = Long.toString(System.currentTimeMillis());
        Cookie cookieLoginCert = new Cookie("loginCert", onlyLoginCert);
        cookieLoginCert.setMaxAge(60 * 60 * 24);
        response.addCookie(cookieLoginCert);

        // redis中添加唯一登录凭证
        jedis.hset(nowUserName, "lc", onlyLoginCert);
        jedis.expire(nowUserName, 60 * 60 * 24);  // 设置自动登录期效
    }

    /**
     * 注册时用户名是否符合限制
     * @param resp 传入最终返回结果类的引用, 进行修改
     */
    public void isRegisterUserName(String userName, CommonResp resp) {

        //判断用户名是否有空格
        if (UserNameLimit.userNameSpace(userName)) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "用户名中不能含有空格; ");
            return;
        }

        // 判断用户名唯一
        if (null != UserNameLimit.existAUser(userName, new UserExample(), userMapper)) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "用户名重复; ");
            return;

        }

        // 判断敏感词
        if (UserNameLimit.userNamePolite(userName, Disallow_wordService.root) == 1) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "含有敏感词; ");  // 原有的信息也不要丢
            return;
        }
    }
    /**
     * 登录时用户名是否符合限制
     * @param resp 传入最终返回结果类的引用, 进行修改
     */
    public void isLoginUserName(String userName, CommonResp resp) {

        //判断用户名是否有空格, 是否有敏感词
        if (UserNameLimit.userNameSpace(userName)
                || UserNameLimit.userNamePolite(userName, Disallow_wordService.root) == 1) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "用户名不存在; ");
            return;
        }

        // 判断是否未注册
        if (null == UserNameLimit.existAUser(userName, new UserExample(), userMapper)) {
            resp.setSuccess(false);
            resp.setMessage("用户名未注册");
            return;
        }
    }

    /**
     * 注册时密码是否符合限制
     * @param resp 传入最终返回结果类的引用, 进行修改
     */
    public void isRegisterPassword(String password, CommonResp resp) {
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
        }
    }

    /**
     * 登录时密码是否符合限制
     * @param resp 传入最终返回结果类的引用, 进行修改
     */
    public void isLoginPassword(User user, CommonResp resp) {
        // 如果用户名已经不对, 直接返回
        if (!resp.isSuccess()) {
            return;
        }

        // 判断密码是否符合限制
        if (PasswordLimit.passWordLimit(user.getPassword()) != 0) {
            resp.setSuccess(false);
            resp.setMessage("密码错误");
            return;
        }

        // 判断密码是否正确
        User oldUser = UserNameLimit.selectAUser(user.getUsername(), new UserExample(), userMapper);
        String nowPassword = Md5Encrypt.mdtEncrypt(user.getPassword(), oldUser.getSalt(), (long) user.getPassword().length());
        if (!Objects.equals(oldUser.getPassword(), nowPassword)) {
            resp.setSuccess(false);
            resp.setMessage("密码错误");
            return;
        }
    }

    /**
     * 加密传入User类中的密码, 并将加密后的密码设置到user中
     */
    public void encryptPassword(User user, String salt) {
        // 传入密码长度作为盐值置乱种子
        user.setPassword(Md5Encrypt.mdtEncrypt(user.getPassword(), salt, (long) user.getPassword().length()));
    }

    /**
     * 使用雪花算法设置盐值
     * @return 返回盐值
     */
    public String setSalt(User user) {
        SnowFlakeIdWorker snowFlakeIdWorker = new SnowFlakeIdWorker(0, 0);
        String salt = Long.toString(snowFlakeIdWorker.nextId());
        user.setSalt(salt);
        return salt;
    }

    /**
     * 添加用户
     */
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

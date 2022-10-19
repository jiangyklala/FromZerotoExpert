package com.jiang.fzte.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.fzte.domain.RecordLog;
import com.jiang.fzte.domain.RecordLogExample;
import com.jiang.fzte.domain.User;
import com.jiang.fzte.domain.UserExample;
import com.jiang.fzte.mapper.RecordLogMapper;
import com.jiang.fzte.mapper.UserMapper;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.util.Md5Encrypt;
import com.jiang.fzte.util.PasswordLimit;
import com.jiang.fzte.util.SnowFlakeIdWorker;
import com.jiang.fzte.util.UserAccountLimit;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RecordLogMapper recordLogMapper;

    public static JedisPool jedisPool;


    @PostConstruct
    public void init() {
        jedisPool = new JedisPool(setJedisPoolConfig(), "124.223.184.187", 6379, 5000, "jiang", 1);
//        initJedisPool(jedisPool);
    }

    /**
     *  获取指定用户的操作记录
     * @param pageNum 要查询的页码
     * @param pageSize 每页多少条
     * @param userAc 用户账号
     * @param status 用户操作状态
     * @param opTimeStart 查询的开始时间
     * @param opTimeEnd 查询的结束时间
     */
    public PageInfo<RecordLog> getRecordLog(Integer pageNum, Integer pageSize, String userAc, String status, Long opTimeStart, Long opTimeEnd) {
        PageHelper.startPage(pageNum, pageSize, true);
        RecordLogExample recordLogExample = new RecordLogExample();
        RecordLogExample.Criteria criteria = recordLogExample.createCriteria();
        criteria.andOpTimeBetween(opTimeStart, opTimeEnd);
        criteria.andOpAcEqualTo(userAc);
        criteria.andStatusEqualTo(status);
        List<RecordLog> recordLogs = recordLogMapper.selectByExample(recordLogExample);
        PageInfo<RecordLog> pageInfo = new PageInfo<>(recordLogs);
        return pageInfo;
    }

    /**
     * 预热
     */
    public void initJedisPool(JedisPool jedisPool) {
        int minIdle = 100;
        List<Jedis> minIdleJedisList = new ArrayList<>(minIdle);
        for (int i = 0; i < minIdle; i++) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                minIdleJedisList.add(jedis);
                jedis.ping();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < minIdle; i++) {
            Jedis jedis = null;
            try {
                jedis = minIdleJedisList.get(i);
                jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 配置连接池
     */
    public JedisPoolConfig setJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);//最大连接对象
        config.setJmxEnabled(true);
        config.setMaxIdle(100);//最大闲置对象
        config.setMinIdle(100);//最小闲置对象
        config.setTestOnBorrow(true); // 向资源池借用连接时是否做有效性检测
        config.setTestOnReturn(true); // 向资源池归还连接时是否做有效性检测
        config.setTestWhileIdle(true); // 是否在空闲资源检测时通过 ping 命令检测连接的有效性,无效连接将被销毁
        config.setTimeBetweenEvictionRuns(Duration.ofSeconds(5));  // 空闲资源的检测周期
        config.setMaxWait(Duration.ofSeconds(5)); // 当资源池连接用尽后，调用者的最大等待时间
        config.setMinEvictableIdleTime(Duration.ofSeconds(10));
        config.setBlockWhenExhausted(true); // 当获取不到连接时应阻塞
        return config;
    }

    /**
     * 在 zset 中添加: score = 用户登录的时间戳, member = 用户账号
     */
    public void userOnline(String userAccount, long time) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.zadd("fU:oL", time, userAccount);  // fzteUser:online
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置用户登录凭证
     */
    public void setOnlyLoginCert(String userAccount, HttpServletResponse response) throws IOException {

        // 头部增加 Cookie
        String onlyLoginCert = Long.toString(System.currentTimeMillis());
        Cookie cookieLoginCert = new Cookie("loginCert", onlyLoginCert);
        cookieLoginCert.setMaxAge(60 * 60 * 24);
        response.addCookie(cookieLoginCert);
//        response.flushBuffer();

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset("fU:" + userAccount, "lc", onlyLoginCert);  // lc : loginCert
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnlyLoginCert2(String userAccount, HttpServletResponse response) {

        // 头部增加 token
        String onlyLoginCert = Long.toString(System.currentTimeMillis());
        response.addHeader("Access-Control-Expose-Headers","token");
        response.addHeader("token", onlyLoginCert);
        System.out.println("token添加成功");

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset("fU:" + userAccount, "lc", onlyLoginCert);  // lc : loginCert
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新用户登录凭证
     */
    public boolean updateLoginCert(String userAccount, String nowUserAccount, HttpServletResponse response) {
        try (Jedis jedis = jedisPool.getResource()) {

            //  此时登录的账号和redis中记录的不同, 直接返回添加新凭证
            if (!Objects.equals(userAccount, nowUserAccount)) {
//                错误的: 删除原来的用户记录, 减少服务器空间消耗
//                jedis.expire("fU:" + userAccount, 0);
                return false;
            }

            // redis中更新 userAccount 有效期
            if (jedis.expire("fU:" + userAccount, 60 * 60 * 24) == 0) {
                // userAccount失效(24小时未登录了), 即找不到这个key
                return false;
            }

            // 更新 cookieUserAccount
            Cookie cookieUserAccount = new Cookie("fzteUser", userAccount);
            cookieUserAccount.setMaxAge(60 * 60 * 24);
            response.addCookie(cookieUserAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 添加用户登录凭证
     */
    public void addLoginCert(String nowUserAccount, HttpServletResponse response) {

        // fzteUser = 账号 创建Cookie
        Cookie cookieUserAccount = new Cookie("fzteUser", nowUserAccount);
        cookieUserAccount.setMaxAge(60 * 60 * 24);
        response.addCookie(cookieUserAccount);

        // redis中更新账号信息的期效(必定存在此hash键, 因为在controller中登陆成功后先添加的唯一登录凭证)
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.expire("fU:" + nowUserAccount, 60 * 60 * 24);  // 设置自动登录期效
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册时账号是否符合限制
     * @param resp 传入最终返回结果类的引用, 进行修改
     */
    public void isRegisterUserAccount(String userAccount, CommonResp resp) {
        
        // 判断账号长度
        if (userAccount.length() < 5 || userAccount.length() > 12) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "账号需要 5 - 12 个位; ");
        }

        // 判断账号是否有中文
        Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9]*$");
        if (!pattern1.matcher(userAccount).matches()) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "账号只允许有数字和大小写字母; ");
        }

        //判断账号是否有空格
        if (UserAccountLimit.userAccountSpace(userAccount)) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "账号中不能含有空格; ");
            return;
        }

        // 判断账号唯一
        if (null != UserAccountLimit.existAUserByAc(userAccount, new UserExample(), userMapper)) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "账号重复; ");
            return;

        }

        // 判断敏感词
        if (UserAccountLimit.userAccountPolite(userAccount, Disallow_wordService.root) == 1) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "含有敏感词; ");  // 原有的信息也不要丢
            return;
        }
    }
    /**
     * 登录时账号是否符合限制
     * @param resp 传入最终返回结果类的引用, 进行修改
     */
    public void isLoginUserAccount(String userAccount, CommonResp resp) {

        // 判断账号长度
        if (userAccount.length() < 5 || userAccount.length() > 12) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "账号需要 5 - 12 个位; ");
        }

        // 判断账号是否有中文
        Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9]*$");
        if (!pattern1.matcher(userAccount).matches()) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "账号不存在; ");
        }

        //判断账号是否有空格, 是否有敏感词
        if (UserAccountLimit.userAccountSpace(userAccount)
                || UserAccountLimit.userAccountPolite(userAccount, Disallow_wordService.root) == 1) {
            resp.setSuccess(false);
            resp.setMessage(resp.getMessage() + "账号不存在; ");
            return;
        }

        // 判断是否未注册
        if (null == UserAccountLimit.existAUserByAc(userAccount, new UserExample(), userMapper)) {
            resp.setSuccess(false);
            resp.setMessage("账号未注册");
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
        // 如果账号已经不对, 直接返回
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
        User oldUser = UserAccountLimit.selectAUserByAc(user.getUseraccount(), new UserExample(), userMapper);  // 这里user在数据库中必定存在: 如果不存在, 在第一个判断中已经返回了
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

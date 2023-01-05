<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [FromZerotoExpert](#fromzerotoexpert)
  - [登录与注册模块](#%E7%99%BB%E5%BD%95%E4%B8%8E%E6%B3%A8%E5%86%8C%E6%A8%A1%E5%9D%97)
    - [注册](#%E6%B3%A8%E5%86%8C)
  - [登录](#%E7%99%BB%E5%BD%95)
    - [自动登录](#%E8%87%AA%E5%8A%A8%E7%99%BB%E5%BD%95)
    - [不允许多个设备同时在线](#%E4%B8%8D%E5%85%81%E8%AE%B8%E5%A4%9A%E4%B8%AA%E8%AE%BE%E5%A4%87%E5%90%8C%E6%97%B6%E5%9C%A8%E7%BA%BF)
    - [允许 PC 和移动端同时在线](#%E5%85%81%E8%AE%B8-pc-%E5%92%8C%E7%A7%BB%E5%8A%A8%E7%AB%AF%E5%90%8C%E6%97%B6%E5%9C%A8%E7%BA%BF)
  - [数据统计与拦截](#%E6%95%B0%E6%8D%AE%E7%BB%9F%E8%AE%A1%E4%B8%8E%E6%8B%A6%E6%88%AA)
    - [如何记录用户的在线状态和离线状态？](#%E5%A6%82%E4%BD%95%E8%AE%B0%E5%BD%95%E7%94%A8%E6%88%B7%E7%9A%84%E5%9C%A8%E7%BA%BF%E7%8A%B6%E6%80%81%E5%92%8C%E7%A6%BB%E7%BA%BF%E7%8A%B6%E6%80%81)
    - [统计网站在线人数](#%E7%BB%9F%E8%AE%A1%E7%BD%91%E7%AB%99%E5%9C%A8%E7%BA%BF%E4%BA%BA%E6%95%B0)
    - [统计网站每日访问IP，PV，UV](#%E7%BB%9F%E8%AE%A1%E7%BD%91%E7%AB%99%E6%AF%8F%E6%97%A5%E8%AE%BF%E9%97%AEippvuv)
    - [防止用户恶意刷屏](#%E9%98%B2%E6%AD%A2%E7%94%A8%E6%88%B7%E6%81%B6%E6%84%8F%E5%88%B7%E5%B1%8F)
  - [日志系统](#%E6%97%A5%E5%BF%97%E7%B3%BB%E7%BB%9F)
    - [一个简单的日志系统](#%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%9A%84%E6%97%A5%E5%BF%97%E7%B3%BB%E7%BB%9F)
    - [日志搜索功能](#%E6%97%A5%E5%BF%97%E6%90%9C%E7%B4%A2%E5%8A%9F%E8%83%BD)
  - [系统故障排查与监控](#%E7%B3%BB%E7%BB%9F%E6%95%85%E9%9A%9C%E6%8E%92%E6%9F%A5%E4%B8%8E%E7%9B%91%E6%8E%A7)
    - [1. 用脚本监控 mysql + redis](#1-%E7%94%A8%E8%84%9A%E6%9C%AC%E7%9B%91%E6%8E%A7-mysql--redis)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->





# FromZerotoExpert

## 登录与注册模块
### 注册

**用户名限制**

+ 不能为空(前端)
+ 不能有空格
+ 不能有敏感词
+ 用户名唯一

**密码限制**

+ 不能为空(前端)
+ 长度
+ 强度(只能用三种字符中至少两种)

**加密密码**

+ 随机盐 + 真盐 + 双重MD5
## 登录
**用户名限制**

+ 不能为空(前端)
+ 是否不存在
    + 不符合"注册"时的限制
    + 未注册

密码限制

+ 不能为空(前端)
+ 错误
    + 不符合"注册"时的限制
    + 密码错误
### 自动登录
  1. **首次登录:** 登录时生成Cookie: fzteUser-userAccount, 同时在 redis 中以hash存储用户信息: key: userAccount 暂无 field 和 value；在我的实现中用户登录成功后先添加唯一登录凭证，这个凭证为 key:userAccount field:lc value:xxx, 所以到添加用户登录凭证时，在redis中一定存在这个账号的信息。而我的用户登录凭证只是需要redis中存在这个key = userAccount，所以此时只需更新其有效期即可。
  2. **二次登录:** 登录时检测Cookie,
     + 若有, Cookie的值即为上次登录的账号，若这次登录的账号和上次相同则更新 Cooike 和 redis 中的键有效期；若不相同，创建新账号的信息（此时不能删除旧账号的信息：若此时旧账号在其他地方登录，那它的信息莫名其妙的就没了，要重新登陆。
     + 如无, 进行1.
  3. **访问首页:** 根据是否有Cookie, 且 Cookie 中的 userAccount 在 redis 中是否过期(防止用户手动更改 Cookie 有效期)实现自动登录
### 不允许多个设备同时在线
将用户登录时的时间戳作为唯一登录凭证，一份作为 Cookie 【loginCert = 时间戳】放在用户的浏览器中，一份记录在redis中：【lc = 时间戳 】（loginCertifate）。

每次用户登录时都会更新这个唯一登录凭证，拦截器中拦截本地的登录凭证和 redis 记录的凭证不同的请求。如果同时有两用户登录，则后面用户的唯一登录凭证是最新的，前一个人只需在下一次操作时用拦截器中拦截即可。

**遇到的问题：**

+ 两个设备都需要在**本地**有一个：能通过其访问到该用户在redis中的信息的变量，于是就把这个变量从原来的雪花ID换成了用户登录的【账号】（当前我的程序还没有区分账号和昵称，后续慢慢改）
  + 已区分

+ 在创建拦截器时，因为拦截器是在Spring Context 初始化之前执行，不能直接注入 Service 对象，但我的拦截判断需要它。虽然通过一些途径能达到我的需求，但这样是不是”走歪路“？

+ 在拦截器判断中我需要用到保存的Cookie，就会导致每次用户在请求时都需要重新查一遍所有Cookie直至找到我要用的Cookie，效率不高？

+ 有些地方的代码我自己都看着臃肿：
  + 后端查找 Cookie 的三种方式


### 允许 PC 和移动端同时在线
需要用到 request 中 key 为 user-agent 的值。思路：扩展”唯一登录用户凭证“，根据当前登录的电脑/手机设备，生成“唯一设备登录凭证”，在本地设置名为 ”PcLoginCert" 或 “MbLoginCert” 的 Cookie，同时也在 redis 用户哈希表中用 “plc” 或 “mlc” 保存对应凭证。拦截器拦截时，根据当前电脑/手机，检测 redis 中相应的凭证是否一致。

## 数据统计与拦截

### 如何记录用户的在线状态和离线状态？

**想法1：** 用户登录时在redis中记录 online=1，用户登出时设置 online=0 。

问题1：如何准确记录用户的登出状态？

+ js中的 onbeforeunload, onunload 不能完全满足需求，`As of Chrome 60, the confirmation will be skipped if the user has not performed a gesture in the frame or page since it was loaded.`  即用户登录后必须在网页中做一些操作，否则不会执行那两个函数

**想法2：** 在用户端向服务器端发送“心跳信息“。

问题1：会导致服务器增加负载

### 统计网站在线人数

1. **我的思路是：** ”心跳信息“ + redis中的 zset (长轮询）；

   + 用户登录网站后每隔一段时间向服务器发送一条”心跳信息“：在 redis 中的有序集合中设置 key = oL(onLine)，score = 发送心跳信息时的时间戳，member = 账号。
   + 网站每隔一段时间访问 /ShowOnlineUsers 接口：在 zset 中查找出 score 在[当前时间戳 - 3秒]到[当前时间戳]的 member，转为 JSON 数组返回到前端显示

   **中间遇到了点困难：** 由于我原先的程序只在 UserService 中设置了一个 jedis 静态成员来访问 redis，哪怕只有一个用户登录时：若某一时刻（发送心跳信息的时间与显示在线人数时间的最小公倍数）就会造成 jedis 访问出问题，这是我第一次实践中遇到并发问题。解决方法是改用 jedis连接池，每次用到 jedis 时从池中获取一个 jedis 实例，用完归还就彳亍。

2. 优点：实现简单
   缺点：用户频繁发出心跳信息会对服务器带来压力，而“当前在线人数显示”可以设置为一个按钮，用户点击时才显示，减轻服务器压力

3. 我在想是否有一种方法，可以在用户登录网站时在redis中当前用户哈希表中添加 ”online“ = ”1“，用户登出时再将其设置为”0“，这是对服务器最友好的方法；但难点在于如何准确的判断用户登出了网站，我目前找到最有希望的方法是在前端使用 window.onbeforeunload, 但是它必须要用户在网站上做一定的操作后才能生效，如果你刚登录就立马关闭网站窗口，这个 onbeforeunload 就无法执行，此时 ”online“ 字段一直就是“1”。

4. 我还找到了一种方法：使用 WebSocket 实现服务器和客户端的全双工通信，缺点是部分浏览器不支持（可以在不支持时用长轮询），目前正在实现中。

5. 10.13 使用 setTimeout 的延时递归来代替 setInterval：setInterval会产生回调堆积，特别是时间很短的时候。  

### 统计网站每日访问IP，PV，UV

**IP:** 通过 HttpServeletRequest 获取；redis中存：fU:ip:当前日期 = ip；使用 HyperLogLog

**PV:** 用户每次刷新网页 + 1； redis中存：fU:pv:当前日期 = 0; 使用 字符串

**UV：** 通过用户浏览器中的 Cookie：【fzteUser = 用户账号】来确认一个访客；redis中存：fU:uv:当前日期 = 用户账号；使用 HyperLogLog

**如何将这些数据保存起来？** 

--> 就存在redis中，设置有效期为 30 天 （存在redis中资源占用）

--> 定时任务：每日0点以后将 redis 中的这些数据转移到 mysql 中，同时 mysql 删除30天以前的数据：使用了 Spring 的定时任务功能：具体见 /timer/IPUVPVTransfer



### 防止用户恶意刷屏

1. **设置某个接口的访问次数限制：**
   + 设计一个注释类 VisitLimit ：sec 时间内只能访问 limit 次（我这里设置的是10秒钟只能访问2次）
   + 在拦截器中拦截： 请求的IP地址是否在白名单内，在则直接跳过限制的判断； 不在则判断拦截的请求对应的接口是否具有 VisitLimit 注解，进行相关判断
2. **用户白名单设计：** 
   + 使用 redis 中的 set, key为 **[fU:wI]**，value为**[用户IP+访问的接口]** 查询复杂度 $O(1)$ 
   + 增加程序运行过程中动态增删用户白名单接口 /AddUserWhiteIP，/DelUserWhiteIP

## 日志系统

### 一个简单的日志系统

**思路：** 基于AOP实现：设计一个需要记录日志的注解类(@LogAnnotation)，则切点即为带有此注解的接口，使用环绕通知的方式进行日志打印，同时将日志记录到 mysql 中(记录操作使用异步线程完成)

**表设计：** 

| 字段     | 说明       |
| -------- | ---------- |
| op_time  | 操作时间   |
| op_type  | 操作类型   |
| op_ac  | 操作用户ID |
| op_ip    | 操作用户IP |
| op_desc  | 操作描述   |
| status   | 是否成功   |
| err_msg  | 异常信息   |
| time_csm | 耗时       |
| req_url  | 请求地址   |
| req_mtd | 类名方法 |

**[10. 17] 遇到的问题： 在对登录(/Login)接口 和 注册(/Register)接口进行日志记录时，无法准确获得用户的账号信息（op_ac）。**

**原因为：** 目前我是在用户访问 /Login 接口时，在用户本地增加一个 Cookie 来记录用户的账号（使用HttpServletResponse.addCookie( ) 方法），但用户访问 /Login 接口时的 HttpServletRequest 不能获得也是在这个接口中添加的 Cookie 信息，即：虽然这个接口的方法中添加了 Cookie，但此时还未同步到 requset 中（大致是这个意思），必须要这个方法完全结束后的下一次 HttpServletRequest 请求中才能生效。

**解决：** 
+ 我尝试了在日志系统的环绕通知中先执行 `Object result = joinPoint.proceed();` 再获取 HttpServletRequest，不行。
+ 使用 @AfterReturning，返回后通知，也不行。
+ 最后我又想可能是这两个接口对于【通用日志记录系统】来说比较特殊，需要单独为他俩写一个日志记录处理方法，然后在这两个方法的返回结果中记录用户的账号信息，这样就能为这两个接口准确记录日志了。目前使用的是这种方法

### 日志搜索功能

一个接口（/GetRecordLog）三个参数（pageSize, opTimeStart, opTimeEnd）

详情查看项目的 APIDoc.md



## 系统故障排查与监控

### 1. 用脚本监控 mysql + redis

**监控 MySQL 思路**

+ 监听 3306 端口
+ 监控 mysqld 进程
+ 同时采用上述两种方法，进程和端口都存在才算 mysql 服务正常 ✓
+ 使用 mysqladmin ping 命令 ✓

**监控 Redis 思路**

+ 监控 redis-server 进程 ✓
+ 使用 ping 命令

**设置定时脚本思路**

+ 在脚本内采用 `do while` 
+ 利用 linux 中的 corn 定时任务 ✓

**shell 代码如下：**

```shell
#!/bin/bash

# 监控 MySQL
# 使用第三种方法
PROCESS_MySQL=`ps -ef | grep mysqld | grep -v grep | wc -l`
PORT=`netstat -tlun tcp -p | grep mysql | grep -v grep | wc -l`

if [ $PORT -lt 1 ] && [ $PROCESS_MySQL -lt 2 ]
then
    echo "\n$(date): MySQL已停止运行,执行自动重启" >> ../log/check_wrong_MySQL.log
    echo "jiang" | sudo /usr/local/mysql/support-files/mysql.server start >> ../log/check_wrong_MySQL.log 2>&1
fi


# 使用第四种方法
#
#MYSQL_USER=jiang # 定义数据库相关变量
#MYSQL_PASSWORD=jiang
#MYSQL_PORT=3306
#MYSQL_HOST=localhost
#
#MYSQL_ADMIN="/usr/local/mysql/bin/mysqladmin -u$MYSQL_USER -p$MYSQL_PASSWORD -P$MYSQL_PORT -h$MYSQL_HOST"
#
#$(${MYSQL_ADMIN}) ping >> /dev/null 2>&1
#
#if [ $? -ne 0 ]
#then
#    echo "\n$(date): MySQL已停止运行,执行自动重启" >> ../log/check_wrong_MySQL.log
#    echo "jiang" | sudo /usr/local/mysql/support-files/mysql.server start >> ../log/check_wrong_MySQL.log 2>&1
#fi


# 监控 Redis
PROCESS_Redis=`ps -ef | grep "redis-server 127.0.0.1:6379" | grep -v grep | wc -l`

if [ $PROCESS_Redis -lt 1 ]
then
    echo "\n$(date): Redis已停止运行，正在重启" >> ../log/check_wrong_Redis.log 2>&1
    redis-server /usr/local/etc/redis.conf
fi

```

**测试结果（事先手动关闭了 MySQL 和 Redis 服务）：**

![](https://xiaoj-1309630359.cos.ap-nanjing.myqcloud.com/202301060133989.png)


















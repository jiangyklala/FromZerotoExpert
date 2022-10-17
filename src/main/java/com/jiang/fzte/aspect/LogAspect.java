package com.jiang.fzte.aspect;


import com.jiang.fzte.annotation.LogAnnotation;
import com.jiang.fzte.domain.RecordLog;
import com.jiang.fzte.domain.User;
import com.jiang.fzte.mapper.RecordLogMapper;
import com.jiang.fzte.resp.CommonResp;
import com.jiang.fzte.util.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class LogAspect {

    @Resource
    private RecordLogMapper recordLogMapper;

    @Pointcut("@annotation(com.jiang.fzte.annotation.LogAnnotation)")
    public void pointCut() {}

    @Pointcut("execution(* com.jiang.fzte.controller.UserController.login(..))")
    public  void loginPointCut() {}

    @Pointcut("execution(* com.jiang.fzte.controller.UserController.register(..))")
    public  void registerPointCut() {}

    @Around("pointCut()")
    public Object aroundPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        long opTime = System.currentTimeMillis();

        // 执行方法
        Object result = joinPoint.proceed();

        // 保存日志
        saveLog(request, joinPoint, opTime, Long.toString(System.currentTimeMillis() - opTime));

        return result;
    }

    @Around("loginPointCut()")
    public Object aroundLoginPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        long opTime = System.currentTimeMillis();

        // 执行方法
        Object result = joinPoint.proceed();

        if (result instanceof CommonResp) {
            CommonResp<User> commonResp = (CommonResp<User>) result;
            User user = commonResp.getContent();
            String userAc = user.getUseraccount();

            // 保存日志
            saveLog(userAc, request, joinPoint, opTime, Long.toString(System.currentTimeMillis() - opTime), "Login", "用户登录");
        }
        return result;
    }

    @Around("registerPointCut()P")
    public Object aroundRegisterPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        long opTime = System.currentTimeMillis();

        // 执行方法
        Object result = joinPoint.proceed();

        if (result instanceof CommonResp) {
            CommonResp<User> commonResp = (CommonResp<User>) result;
            User user = commonResp.getContent();
            String userAc = user.getUseraccount();

            // 保存日志
            saveLog(userAc, request, joinPoint, opTime, Long.toString(System.currentTimeMillis() - opTime), "Register", "用户注册");
        }
        return result;
    }

    @Async
    void saveLog(HttpServletRequest request, ProceedingJoinPoint joinPoint, Long opTime, String timeCsm) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);  // 注释类
        Cookie fzteUser = WebUtils.getCookie(request, "fzteUser");  // 获取用户ID
        RecordLog recordLog = new RecordLog();

        try {
            // 记录信息
            recordLog.setOpType(logAnnotation.opType());
            recordLog.setOpDesc(logAnnotation.opDesc());
            recordLog.setOpIp(IpUtils.getIpAddr(request));
            recordLog.setReqUrl(request.getRequestURI().toString());
            recordLog.setReqMtd(joinPoint.getTarget().getClass().getName() + "." + signature.getName());
            recordLog.setOpTime(opTime);
            recordLog.setTimeCsm(timeCsm);
            if (fzteUser != null) recordLog.setOpAc(fzteUser.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

        recordLog.setStatus("success");

        recordLogMapper.insert(recordLog);

    }

    @Async
    void saveLog(String userAc, HttpServletRequest request, ProceedingJoinPoint joinPoint, Long opTime, String timeCsm, String opType, String opDesc) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);  // 注释类
        RecordLog recordLog = new RecordLog();

        try {
            // 记录信息
            recordLog.setOpAc(userAc);
            recordLog.setOpType(opType);
            recordLog.setOpDesc(opDesc);
            recordLog.setOpIp(IpUtils.getIpAddr(request));
            recordLog.setReqUrl(request.getRequestURI().toString());
            recordLog.setReqMtd(joinPoint.getTarget().getClass().getName() + "." + signature.getName());
            recordLog.setOpTime(opTime);
            recordLog.setTimeCsm(timeCsm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recordLog.setStatus("success");

        recordLogMapper.insert(recordLog);

    }

    private void getNewAc(HttpServletRequest request) {
        Cookie fzteUser = WebUtils.getCookie(request, "fzteUser");  // 获取用户ID
        System.out.println(fzteUser.getValue());
        return;
    }

}

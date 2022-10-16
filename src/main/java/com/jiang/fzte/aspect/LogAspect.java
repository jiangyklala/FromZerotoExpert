package com.jiang.fzte.aspect;


import com.jiang.fzte.annotation.LogAnnotation;
import com.jiang.fzte.domain.RecordLog;
import com.jiang.fzte.mapper.RecordLogMapper;
import com.jiang.fzte.util.IpUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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

    @Around("pointCut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long opTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 执行方法
        Object result = joinPoint.proceed();

        // 保存日志
        saveLog(request, joinPoint, opTime, Long.toString(System.currentTimeMillis() - opTime));

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
            recordLog.setStatus("failed");
        }

        recordLog.setStatus("success");

        recordLogMapper.insert(recordLog);

    }

}

package com.jiang.fzte.annotation;

import java.lang.annotation.*;


/**
 * 接口限流
 */
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitLimit {

    // 标识: 指定 sec 时间段内访问次数的限制
    int limit() default 1;

    // 标识: 时间段
    long sec() default  5;
}

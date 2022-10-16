package com.jiang.fzte.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    // 操作类型
    String opType() default "";

    // 操作说明
    String opDesc() default "";

}

package com.lx.shorturl.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * @Author: lx
 * @Date: 2019/9/26 15:46
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {

    int count() default 5;

    long time() default 60*1000;
}

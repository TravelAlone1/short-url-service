package com.lx.shorturl.aspect;

import com.lx.shorturl.annotation.RequestLimit;
import com.lx.shorturl.utils.HttpRequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lx
 * @Date: 2019/9/26 15:55
 */

@Component
@Aspect
public class RequestLimitAspect {






}

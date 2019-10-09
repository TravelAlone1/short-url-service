package com.lx.shorturl.controller;

import com.google.common.base.Strings;
import com.lx.shorturl.annotation.RequestLimit;
import com.lx.shorturl.entity.Link;
import com.lx.shorturl.service.LinkService;
import com.lx.shorturl.service.RedisService;
import com.lx.shorturl.utils.HttpRequestUtil;
import com.lx.shorturl.utils.Result;
import com.lx.shorturl.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: lx
 * @Date: 2019/9/25 16:18
 */
@Controller
@Api(value = "用户类控制器",tags="用户类控制器")
public class LinkController {

    private Logger log = LoggerFactory.getLogger(LinkController.class);

    static final String shortKey="shortUrl:";

    static final String hello="helloWorld";

    private static final Long expireTime=60L;

    @Autowired
    private LinkService linkService;

    @Autowired
    private RedisService redisService;

    /**
     * 生成短链接
     *
     * @param link
     * @return Caron
     */

    @ApiOperation(value = "获取用户列表",notes = "获取用户列表")
    @PostMapping("/api/create")
    @ResponseBody
    public Object save(@RequestBody Link link) {

        System.out.println("接口调用次数");
        String longUrl = link.getLongUrl();
        if (Strings.isNullOrEmpty(longUrl)) {
            return Result.error();
        }

        if (longUrl.startsWith("http://") || longUrl.startsWith("https://")) {

            linkService.save(link);
            return Result.ok(link);
        } else {
            return Result.error();
        }
    }

    /**
     * 301跳转
     *
     * @param shortCode
     * @return
     */
    @RequestMapping("/lx/{shortCode}")
    public void jump(@PathVariable("shortCode") String shortCode, HttpServletResponse response) {
        Link link = linkService.restoreUrl(shortCode);
        System.out.println(link);

        if (link != null && !Strings.isNullOrEmpty(link.getLongUrl())) {
            response.setStatus(302);
            try {
                response.sendRedirect(link.getLongUrl());
                Date date = new Date();
                String strDateFormat = "yyyyMMddHHmm";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                String format = sdf.format(date);

                //redisService.increment(shortKey+shortCode,null);
                Long count = redisService.hincr(format, shortCode, 1L);
                if (count>10){
                    log.error("接口调用次数达到限制");
                }

                response.sendRedirect(link.getLongUrl());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.getWriter().write("404");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestLimit(count = 4)
    @ResponseBody
    @GetMapping("/index")
    public Object index() {
        return 1;
    }


    @GetMapping("/my/hello")
    @ResponseBody
    public String hello(HttpServletRequest request){
        Date date = new Date();
        String strDateFormat = "yyyyMMddHHmm";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        //Long count = redisService.incrBy(hello+sdf.format(date), 1L);
        String ipAddr = HttpRequestUtil.getIpAddr(request);
        Long count = redisService.hincr(sdf.format(date), ipAddr, 1L);

        System.out.println("count: "+count);
        if (count>10){
            return "调用次数已经达到限制";
        }
        return "林晓";
    }

    @GetMapping("/lx/test")
    @ResponseBody
    public String test(HttpServletRequest request){
        Date date = new Date();
        String strDateFormat = "yyyyMMddHHmm";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String format = sdf.format(date);
        String ipAddr = HttpRequestUtil.getIpAddr(request);
        Long incr = redisService.incrBy(format + ipAddr, 1L);
        System.out.println("Date incr: "+format+incr);
        if (incr>10){
            return "ip调用次数达到限制";
        }
        return "success";
    }
}

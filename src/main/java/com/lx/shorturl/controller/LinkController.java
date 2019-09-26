package com.lx.shorturl.controller;

import com.google.common.base.Strings;
import com.lx.shorturl.entity.Link;
import com.lx.shorturl.service.LinkService;
import com.lx.shorturl.service.RedisService;
import com.lx.shorturl.utils.Result;
import com.lx.shorturl.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: lx
 * @Date: 2019/9/25 16:18
 */
@Controller
public class LinkController {

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
    @PostMapping("/api/create")
    @ResponseBody
    public Object save(@RequestBody Link link) {

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
    @RequestMapping("/{shortCode}")
    public void jump(@PathVariable("shortCode") String shortCode, HttpServletResponse response) {
        Link link = linkService.restoreUrl(shortCode);
        System.out.println(link);

        if (link != null && !Strings.isNullOrEmpty(link.getLongUrl())) {
            response.setStatus(302);
            try {
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

}

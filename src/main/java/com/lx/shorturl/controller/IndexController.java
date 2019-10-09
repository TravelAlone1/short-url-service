package com.lx.shorturl.controller;

import com.lx.shorturl.entity.Echars;
import com.lx.shorturl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: lx
 * @Date: 2019/9/27 21:27
 */
@Controller
public class IndexController {

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/EcharsSho")
    @ResponseBody
    public List<Echars> findById(Model model) {
        List<Echars> list = new ArrayList<Echars>();
        list.add(new Echars("帽子",100));
        list.add(new Echars("鞋子",126));
        list.add(new Echars("毛衣",75));
        list.add(new Echars("羽绒服",201));
        list.add(new Echars("羊毛衫",172));
        System.err.println(list.toString());

        return list;
    }

    @RequestMapping(value = "/EcharsShow")
    @ResponseBody
    public List<Echars> findBy(Model model) {
        List<Echars> list = new ArrayList<Echars>();
        System.out.println("执行此方法");
        List<String> keys = redisService.keys("shortUrl*");
        String s = keys.toString();
        System.out.println(s);

        for (String key: keys){
            list.add(new Echars(key,Integer.parseInt(redisService.get(key))));
        }
        System.err.println(list.toString());
        return list;
    }

//    @RequestMapping(value = "/EcharsShow")
//    @ResponseBody
//    public List<Echars> find(Model model) {
//
//    }


    @GetMapping(value = "/Echars.do")
    public String echarts4(Model model){
        System.err.println("========开始");
        return "Echars";
    }

}

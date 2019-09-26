package com.lx.shorturl.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author: lx
 * @Date: 2019/9/26 0:39
 */
public class Utils {


    public static Date getMaxTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2099, 12, 31); //年月日  也可以具体到时分秒如calendar.set(2015, 10, 12,11,32,52); 
        Date date = calendar.getTime();//date就是你需要的时间
        return date;
    }
}

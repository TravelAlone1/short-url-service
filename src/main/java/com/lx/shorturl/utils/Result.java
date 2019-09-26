package com.lx.shorturl.utils;

import java.util.HashMap;

/**
 * @Author: lx
 * @Date: 2019/9/25 11:35
 */
public class Result extends HashMap<String,Object> {


    public Result(){

    }

    public Result(ResultCode resultCode){
        put("code",resultCode.code());
        put("msg",resultCode.message());
    }

    public static Result ok(Object data){
        Result result = new Result(ResultCode.SUCCESS);
        result.put("data",data);
        return result;
    }

    public static Result error(){
        Result result = new Result(ResultCode.ERROR);
        return result;
    }

    public static Result error(ResultCode resultCode){
        Result result = new Result(resultCode);

        return result;
    }


}

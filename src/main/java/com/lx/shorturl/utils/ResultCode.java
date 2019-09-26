package com.lx.shorturl.utils;

/**
 * @Author: lx
 * @Date: 2019/9/26 0:12
 */
public enum ResultCode {

    SUCCESS(10000,"sussess"),
    ERROR(-10000,"system error"),
    NOT_FOUND(404,"not found");
    private Integer code;

    private String message;



    ResultCode(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    public Integer code(){
        return this.code;
    }

    public String message(){
        return this.message;
    }

    public static String getMessage(String name){
        for(ResultCode item : ResultCode.values()){
            if (item.name().equals(name)){
                return item.message;
            }
        }
        return null;
    }

    public static Integer getCode(String name){
        for(ResultCode item : ResultCode.values()){
            if(item.name().equals(name)){
                return item.code;
            }
        }
        return null;
    }


}

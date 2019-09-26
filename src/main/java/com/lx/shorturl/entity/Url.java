package com.lx.shorturl.entity;

import lombok.Data;

/**
 * @Author: lx
 * @Date: 2019/9/25 11:32
 */
@Data
public class Url {

    private String id;

    private String url;


    @Override
    public String toString(){
        return new StringBuilder()
                .append("id: "+id)
                .append("url: "+url)
                .toString();
    }
}

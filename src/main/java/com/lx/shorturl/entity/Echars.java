package com.lx.shorturl.entity;

import lombok.Data;

/**
 * @Author: lx
 * @Date: 2019/9/27 22:04
 */
@Data
public class Echars {
    private String name;
    private Integer num;

    public Echars(String name, Integer num) {
        super();
        this.name = name;
        this.num = num;
    }
}

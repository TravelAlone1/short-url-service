package com.lx.shorturl.service;

/**
 * @Author: lx
 * @Date: 2019/9/25 11:42
 */
public interface Generator {

    public int init();

    Long next();
}

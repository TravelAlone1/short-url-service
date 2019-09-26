package com.lx.shorturl.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
*@Author: lx
*@Date: 2019/9/25 16:07
*/  
@Data
public class Link implements Serializable {

    private static final long serialVersionUID = -8289770787953160443L;
    /**
    * 主键
    */
    private String shortCode;

    /**
    * 长链接
    */
    private String longUrl;

    /**
    * 短链接
    */
    private String shortUrl;

    private Date createTime;

    private Date updateTime;

    private Date expireTime;

    private int status;

}
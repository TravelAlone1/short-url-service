package com.lx.shorturl.service;

import com.lx.shorturl.entity.Link;

/**
 * @Author: lx
 * @Date: 2019/9/25 16:09
 */
public interface LinkService {

    void save(Link link);

    Link restoreUrl(String url);
}

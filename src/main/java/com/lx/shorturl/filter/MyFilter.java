package com.lx.shorturl.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @Author: lx
 * @Date: 2019/9/28 11:22
 */
@WebFilter(filterName = "myFilter" , urlPatterns = "/*")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init....");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        String requestURL = request.getRequestURI();
        if (requestURL.contains("my")){
            System.out.println("成功了。。。。"+requestURL);
        }
        filterChain.doFilter(request,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("destroy.....");
    }
}

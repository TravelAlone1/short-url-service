package com.lx.shorturl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: lx
 * @Date: 2019/9/28 13:30
 */
@Configuration
public class SpringMvcWebConfigSupport implements WebMvcConfigurer {


//    public void addViewController(ViewControllerRegistry registry){
//        registry.addViewController("/").s
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //实现WebMvcConfigurationSupport之后，静态文件映射会出现问题，需要重新指定静态资源
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}

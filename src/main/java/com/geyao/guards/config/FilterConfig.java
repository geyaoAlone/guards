package com.geyao.guards.config;

import com.geyao.guards.manage.filter.ManageFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
public class FilterConfig{

    @Bean
    public FilterRegistrationBean firstFilter() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我们写好的过滤器
        registration.setFilter(new FirstFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;

    }


    @Bean
    public FilterRegistrationBean SecondManageFilter() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我们写好的过滤器
        registration.setFilter(new ManageFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/manage/*");
        registration.setOrder(2);
        return registration;

    }


}

package com.xdd.init.jwt;

import com.xdd.init.jwt.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置类
 */
@Configuration
public class FilterConfig {



    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> loggingFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtAuthenticationFilter);
        registrationBean.addUrlPatterns("/api/*"); // 设置需要过滤的 URL 模式
//        registrationBean.addUrlPatterns("/"); // 设置需要过滤的 URL 模式


        return registrationBean;
    }
}

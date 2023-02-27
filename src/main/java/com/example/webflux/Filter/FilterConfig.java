package com.example.webflux.Filter;

import com.example.webflux.EventNotify;
import com.example.webflux.Filter.MyFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
@Slf4j
@Configuration
public class FilterConfig {

    @Autowired
    public EventNotify eventNotify;

    @Bean
    public FilterRegistrationBean<Filter> addFilter(){
        log.info("\n**********\nadd filter \n**********");
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new MyFilter(eventNotify));
        bean.addUrlPatterns("/sse");
        return bean;
    }
    @Bean
    public FilterRegistrationBean<Filter> addFilter2(){
        log.info("\n**********\nadd filter \n**********");
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new MyFilter2(eventNotify));
        bean.addUrlPatterns("/add");
        return bean;
    }
}

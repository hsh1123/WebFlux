package com.example.webflux.Filter;

import com.example.webflux.EventNotify;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyFilter2 implements Filter {

    private EventNotify eventNotify;
    public MyFilter2(EventNotify eventNotify){
        this.eventNotify = eventNotify;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("\n**********\nstart filter2 \n**********");
        //데이터를 하나 발생시켜서
        eventNotify.add("새로운 데이터");
    }
}

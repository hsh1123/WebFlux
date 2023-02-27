package com.example.webflux.Filter;

import com.example.webflux.EventNotify;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyFilter implements Filter {

    private EventNotify eventNotify;

    public MyFilter(EventNotify eventNotify){
        this.eventNotify = eventNotify;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("\n**********\nstart filter \n**********");

        HttpServletResponse servletResponse = (HttpServletResponse) response;
//        servletResponse.setContentType("text/plain; charset=utf-8"); // text/plain; 일 경우 데이터를 쌓아놓고 마지막에 데이터를 뿌린다.
        servletResponse.setContentType("text/event-stream; charset=utf-8"); // text/event-stream; stream을 열어 놓고 데이터를 계속 준다
        PrintWriter out = servletResponse.getWriter();


        //1. Reactive Streams library를 통해 표준을 지켜서 응답을 할 수 있다.
        for(int i=0; i<5; i++){
            out.print("응답 : " + i + "\n");
            out.flush(); // 버퍼를 비움
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //2.SSE(Server-Send-Event) Emitter Library를 사용하면 편하게 쓸 수 있다.
        while (true){
            try {
                if(eventNotify.getChange()){
                    int lastIndex = eventNotify.getEvents().size() -1;
                    out.print("응답"+ eventNotify.getEvents().get(lastIndex) + "\n");
                    out.flush();
                    eventNotify.setChange(false);
                }
                Thread.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        //3. Web Flux > Reative Streams가 적용된 stream을 배우고(비동기 단일 스레드 동작)
        //4. Servlet MVC > Reative Streams가 적용된 stream을 배우고(멀티 스레드 방식)
    }
}

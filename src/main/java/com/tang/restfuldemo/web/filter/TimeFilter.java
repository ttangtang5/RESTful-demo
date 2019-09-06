package com.tang.restfuldemo.web.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description filter demo
 * @Author tang
 * @Date 2019-08-21 21:15
 * @Version 1.0
 **/
@Component
public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("timeFilter init ===");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入timeFilter ===");

        long startTime = System.currentTimeMillis();

        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("filter: 耗时=" + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println("timeFilter ===");
    }

    @Override
    public void destroy() {
        System.out.println("timeFilter destroy ===");
    }
}

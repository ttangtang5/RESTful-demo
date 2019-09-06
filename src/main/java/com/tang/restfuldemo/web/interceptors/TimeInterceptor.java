package com.tang.restfuldemo.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description interceptor demo interceptor 可以获取原始的request、response和handler信息 1、注入容器 2、加入interceptor链
 * @Author tang
 * @Date 2019-08-21 21:25
 * @Version 1.0
 **/
@Component
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入 TimeInterceptor ===");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        System.out.println("处理类 = " + handlerMethod.getMethod().getDeclaringClass().getName());
        System.out.println("处理方法 = " + handlerMethod.getMethod().getName());

        long startTime = System.currentTimeMillis();

        // 或者用localThread
        request.setAttribute("startTime", startTime);

        // true 调用handle false不调用
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 不存在异常 执行
        System.out.println("TimeInterceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 无论是否存在异常 均执行 有异常 异常会存于ex 注意存在ex可能被controllerAdvice（异常处理）处理的情况
        // 拦截执行链：filter->interceptor->controllerAdvice->aspect->controllerAdvice->interceptor->filter
        long startTime = (long) request.getAttribute("startTime");

        System.out.println("TimeInterceptor: 耗时=" + (System.currentTimeMillis() - startTime) + " ms");
    }
}

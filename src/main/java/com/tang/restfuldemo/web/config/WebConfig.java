package com.tang.restfuldemo.web.config;

import com.tang.restfuldemo.web.filter.TimeFilter;
import com.tang.restfuldemo.web.interceptors.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @Author tang
 * @Date 2019-08-21 21:19
 * @Version 1.0
 **/
@Configuration
public class WebConfig implements  WebMvcConfigurer{

    @Autowired
    private TimeInterceptor timeInterceptor;

    /**
     * 手动添加Filter 这样配置可以设置filter的拦截规则  在filter未被comment注解时
     * @return
     */
    //@Bean
    public FilterRegistrationBean timeFilter() {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();

        filterBean.setFilter(new TimeFilter());

        //filterBean.addUrlPatterns("/*");

        return filterBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor).addPathPatterns("/**").excludePathPatterns("/**/swagger*");
    }

    //@Override
    //public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    //    // 设置异步interceptor  实现DeferredResultProcessingInterceptor接口
    //    //configurer.registerDeferredResultInterceptors()；
    //    // 超时时间
    //    //configurer.setDefaultTimeout()
    //}
}

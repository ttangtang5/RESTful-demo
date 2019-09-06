package com.tang.restfuldemo.errors;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @Description 配置自定义错误页面。参考：https://blog.csdn.net/trusause/article/details/84299886
 * @Author tang
 * @Date 2019-08-21 16:02
 * @Version 1.0
 **/
//@Configuration
public class ErrorConfig implements ErrorPageRegistrar {


    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/errors/404");
        ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errors/500");

        registry.addErrorPages(error404, error500);
    }
}

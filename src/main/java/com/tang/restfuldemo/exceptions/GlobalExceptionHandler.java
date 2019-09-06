package com.tang.restfuldemo.exceptions;

import com.tang.restfuldemo.dto.BaseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @Description 全局异处理
 * @Author tang
 * @Date 2019-08-20 22:03
 * @Version 1.0
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404  //TODO 暂未处理好
     * @return
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @RequestMapping(produces = "text/html")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String unfound() {
        return "404";
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResult custom(CustomException ex) {
        BaseResult baseResult = new BaseResult();

        baseResult.setCode("500");
        baseResult.setMessage(ex.getMessage());

        return baseResult;
    }
}

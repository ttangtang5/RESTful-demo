package com.tang.restfuldemo.exceptions;

/**
 * @Description 自定义异常
 * @Author tang
 * @Date 2019-08-20 22:15
 * @Version 1.0
 **/
public class CustomException extends RuntimeException {

    private String code;

    private String message;

    public CustomException() {
        super();
    }

    public CustomException(String message, String code) {
        super(message);
        this.code = code;
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

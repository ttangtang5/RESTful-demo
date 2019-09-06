package com.tang.restfuldemo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 自定义valid注解
 * @Author tang
 * @Date 2019-08-21 15:18
 * @Version 1.0
 **/
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomValidatorHandler.class) //validation 里面的 作用：指定当前注解用那个类校验，执行的校验逻辑
public @interface CustomValidator {

    //这些属性是必备的  直接去其他注解copy
    String message() default "这是个自定义校验注解";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

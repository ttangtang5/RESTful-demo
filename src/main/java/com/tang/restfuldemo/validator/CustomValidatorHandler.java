package com.tang.restfuldemo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * @Description 用于CustomValidator注解校验  泛型<A, T>: A为需作用的注解，T表示支持注解验证的字段类型，如果为String 即只能校验string类型的。
 *  此类可以通过spring 容器注入对象
 * @Author tang
 * @Date 2019-08-21 15:32
 * @Version 1.0
 **/
public class CustomValidatorHandler implements ConstraintValidator<CustomValidator, Object> {

    @Override
    public void initialize(CustomValidator constraintAnnotation) {
        System.out.println("custom validator init");
    }

    /**
     * 校验方法
     * @param o 传进来的值
     * @param constraintValidatorContext 校验上下文
     * @return true为校验成功
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        // 校验逻辑
        System.out.println("校验值 = " + o);

        System.out.println(" 判断传入的值是否符合 ");

        return false;
    }
}

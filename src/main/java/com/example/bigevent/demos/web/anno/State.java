package com.example.bigevent.demos.web.anno;

import com.example.bigevent.demos.web.validation.StateValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented//元注解
@Constraint(validatedBy = {StateValidation.class})//指定提供校验规则的类
@Target({FIELD})//元注解
@Retention(RUNTIME)//元注解

public @interface State {
    //提供校验失败后的提示信息
    String message() default "{参数只能是已发布或草稿}";
    //定义分组
    Class<?>[] groups() default {};
    //负载
    Class<? extends Payload>[] payload() default {};
}

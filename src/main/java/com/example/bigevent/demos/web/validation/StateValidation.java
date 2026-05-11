package com.example.bigevent.demos.web.validation;

import com.example.bigevent.demos.web.anno.State;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {
    //   @Param value:将来要校验的数据
    //@Param
    //  context:校验的上下文
    //  返回值:校验结果
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null) return false;
        if(value.equals("已发布")||value.equals("草稿")) return true;
        return false;
    }
}

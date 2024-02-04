package com.xiudu.blog.util.Validator.allNotEmpty;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author: 锈渎
 * @date: 2024/2/1 15:08
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 自定义参数校验 - 判断传递的所有参数是否都有值
 */
@Slf4j
public class AllNotEmptyValidator implements ConstraintValidator<AllNotEmpty, Object> {
    private String[] fields;
    @Override
    public void initialize(AllNotEmpty constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if(object == null) return true;
        try {
            for(String fieldName : fields) {
                Object property = getField(object, fieldName);
                if(property == null || "".equals(property))  return false; // 有一个参数没值时返回错误
            }
            return true;
        } catch (Exception e) {
            log.error("参数校验错误: {}", e.getMessage());
            return false;
        }
    }

    private Object getField(Object object, String fieldName) throws IllegalAccessException {
        if(object == null) return null;
        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields) {
            if(field.getName().equals(fieldName)) {
                field.setAccessible(true);
                return field.get(object);
            }
        }
        return null;
    }

}

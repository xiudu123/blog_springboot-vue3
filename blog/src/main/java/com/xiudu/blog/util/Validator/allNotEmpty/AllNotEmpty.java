package com.xiudu.blog.util.Validator.allNotEmpty;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by 锈渎 on 2024/2/1 15:02
 */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = AllNotEmptyValidator.class)
@Documented
public @interface AllNotEmpty {
    String message() default "{所有值不可为空}";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] fields();
}

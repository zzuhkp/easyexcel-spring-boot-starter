package com.zzuhkp.easyexcel.validator;

import com.zzuhkp.easyexcel.model.DemoData;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author hkp
 * @date 2022/6/6 11:19 AM
 * @since 1.0
 */
@Documented
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {DemoDataValid.DemoDataValidator.class})
public @interface DemoDataValid {

    String message() default "{com.zzuhkp.easyexcel.validator.DemoDataValid.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class DemoDataValidator implements ConstraintValidator<DemoDataValid, DemoData> {

        @Override
        public boolean isValid(DemoData value, ConstraintValidatorContext context) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("测试对象校验").addConstraintViolation();
            return false;
        }
    }

}

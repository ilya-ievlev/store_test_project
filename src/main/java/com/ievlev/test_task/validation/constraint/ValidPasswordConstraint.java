package com.ievlev.test_task.validation.constraint;


import com.ievlev.test_task.validation.ValidPasswordValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidPasswordValidation.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPasswordConstraint {
    String message() default "the password does not comply with password rules";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

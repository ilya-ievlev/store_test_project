package com.ievlev.test_task.validation.constraint;

import com.ievlev.test_task.validation.ConfirmPasswordIsCorrectValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConfirmPasswordIsCorrectValidation.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPasswordIsCorrectConstraint {
    String message() default "password and confirm password does not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

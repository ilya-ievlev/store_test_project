package com.ievlev.test_task.validation.constraint;

import com.ievlev.test_task.validation.UsernameSymbolsIsCorrectValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameSymbolsIsCorrectValidation.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameSymbolsIsCorrectConstraint {
    String message() default "name symbols is not correct";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.ievlev.test_task.validation;

import com.ievlev.test_task.validation.constraint.ValidPasswordConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidPasswordValidation implements ConstraintValidator<ValidPasswordConstraint, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("[a-zA-z0-9]");
        //simple validation that can be changed if needed. I did it because I don't want to have any strange symbols or whitespaces in password
    }
}

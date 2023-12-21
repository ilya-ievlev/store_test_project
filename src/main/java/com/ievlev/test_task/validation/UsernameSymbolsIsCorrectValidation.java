package com.ievlev.test_task.validation;

import com.ievlev.test_task.validation.constraint.UsernameSymbolsIsCorrectConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameSymbolsIsCorrectValidation implements ConstraintValidator<UsernameSymbolsIsCorrectConstraint, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("[a-zA-Z0-9]");
        //simple validation that can be changed if needed. I did it because I don't want to have any strange symbols or whitespaces in username
    }
}

package com.ievlev.test_task.validation;

import com.ievlev.test_task.dto.RegistrationUserDto;
import com.ievlev.test_task.validation.constraint.ConfirmPasswordIsCorrectConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordIsCorrectValidation implements ConstraintValidator<ConfirmPasswordIsCorrectConstraint, RegistrationUserDto> {

    @Override
    public boolean isValid(RegistrationUserDto registrationUserDto, ConstraintValidatorContext constraintValidatorContext) {
        return registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword());
    }
}

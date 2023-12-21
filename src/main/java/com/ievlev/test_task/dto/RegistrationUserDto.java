package com.ievlev.test_task.dto;

import com.ievlev.test_task.validation.constraint.ConfirmPasswordIsCorrectConstraint;
import com.ievlev.test_task.validation.constraint.UsernameSymbolsIsCorrectConstraint;
import com.ievlev.test_task.validation.constraint.ValidPasswordConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ConfirmPasswordIsCorrectConstraint
@Data
public class RegistrationUserDto {
    @NotNull
    @Size(min = 1, max = 30)
    @UsernameSymbolsIsCorrectConstraint
    private String username;

    @NotNull
    @Size(min = 3, max = 25)
    @ValidPasswordConstraint
    private String password;

    @NotNull
    @Size(min = 3, max = 25)
    private String confirmPassword;

}

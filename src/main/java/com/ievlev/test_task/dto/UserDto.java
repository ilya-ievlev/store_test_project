package com.ievlev.test_task.dto;

import com.ievlev.test_task.validation.constraint.UsernameSymbolsIsCorrectConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserDto {
    @Min(0)
    private long id;

    @NotNull
    @UsernameSymbolsIsCorrectConstraint
    private String username;

}

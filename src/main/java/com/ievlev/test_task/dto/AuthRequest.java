package com.ievlev.test_task.dto;

import com.ievlev.test_task.validation.constraint.UsernameSymbolsIsCorrectConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AuthRequest {

    @NotNull
    @Size(min = 1, max = 30)
    @UsernameSymbolsIsCorrectConstraint
    private String username;

    @NotNull
    @Size(min = 1, max = 30)
    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthRequest() {
    }
}

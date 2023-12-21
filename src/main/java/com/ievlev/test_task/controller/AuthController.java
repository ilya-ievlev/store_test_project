package com.ievlev.test_task.controller;

import com.ievlev.test_task.dto.AuthRequest;
import com.ievlev.test_task.dto.JwtResponse;
import com.ievlev.test_task.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/v1/auth")
    public JwtResponse createAuthToken(@RequestBody AuthRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

//    @PostMapping("/registration")
//    public UserDto createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
//        return authService.createNewUser(registrationUserDto);
//    }

}

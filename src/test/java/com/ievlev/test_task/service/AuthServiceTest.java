package com.ievlev.test_task.service;

import com.ievlev.test_task.dto.AuthRequest;
import com.ievlev.test_task.dto.CustomUserDetailsDto;
import com.ievlev.test_task.util.JwtTokenUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserService userService;
    @Mock
    private JwtTokenUtils jwtTokenUtils;


    @Test
    public void createTokenShouldThrowAuthenticationExceptionIfAuthenticationFails() {
        Mockito.doThrow(AuthenticationException.class).when(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        Assertions.assertThrows(AuthenticationException.class, () -> authService.createAuthToken(Mockito.any(AuthRequest.class)));
    }

    @Test
    public void createTokenShouldThrowExceptionIfLoadUserByUsernameCantFindUserInDb(){
        Mockito.doThrow(UsernameNotFoundException.class).when(userService).loadUserByUsername(Mockito.anyString());
        Assertions.assertThrows(UsernameNotFoundException.class, ()->authService.createAuthToken(new AuthRequest("username", "password")));
    }

    @Test
    public void createAuthTokenShouldThrowExcpetionIfAuthRequestIsNull(){
        Assertions.assertThrows(IllegalArgumentException.class, ()-> authService.createAuthToken(null));
    }

    @Test
    public void createAuthTokenShouldCreateCorrectTokenAndReturnIt(){

    }
}

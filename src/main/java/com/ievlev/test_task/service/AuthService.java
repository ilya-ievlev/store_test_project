package com.ievlev.test_task.service;

import com.ievlev.test_task.dto.AuthRequest;
import com.ievlev.test_task.dto.CustomUserDetailsDto;
import com.ievlev.test_task.dto.JwtResponse;
import com.ievlev.test_task.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public JwtResponse createAuthToken(AuthRequest authRequest) {
        if (authRequest == null) {
            throw new IllegalArgumentException("auth request can't be null");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); //check if login and password is correct, if not that throw BadCredentialsException
        CustomUserDetailsDto userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }

//    public UserDto createNewUser(@Valid RegistrationUserDto registrationUserDto) {
//        if (registrationUserDto == null) {
//            throw new IllegalArgumentException("registrationUserDto can't be null");
//        }
//        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
//            throw new UsernameAlreadyExistsException(String.format("username:%s exists", registrationUserDto.getUsername()));
//        }
//        User user = userService.createNewUser(registrationUserDto);
//        return new UserDto(user.getId(), user.getUsername());
//    }
}

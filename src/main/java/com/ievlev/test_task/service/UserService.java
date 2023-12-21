package com.ievlev.test_task.service;

import com.ievlev.test_task.dto.CustomUserDetailsDto;
import com.ievlev.test_task.exceptions.UserIdNotFoundException;
import com.ievlev.test_task.model.User;
import com.ievlev.test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static final String USER_NOT_FOUND = "user '%s' not found";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String USERNAME_S_ALREADY_EXISTS = "username %s already exists";
    private final UserRepository userRepository;
//    private final RoleService roleService;
//    private final PasswordEncoder passwordEncoder;

    public User getByUsername(@NotNull String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
    }

    @Override
    @Transactional
    public CustomUserDetailsDto loadUserByUsername(String username) {
        User user = getByUsername(username);
        return new CustomUserDetailsDto(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()),
                user.getId().toString()
        );
    }

//    public Optional<User> findUserById(long id){
//        return userRepository.findById(id);
//    }


    //    public User createNewUser(@Valid RegistrationUserDto registrationUserDto) {
//        String username = registrationUserDto.getUsername();
//        if(userRepository.findByUsername(username).isPresent()){
//            throw new UsernameAlreadyExistsException(String.format(USERNAME_S_ALREADY_EXISTS, registrationUserDto.getUsername()));
//        }
//        User user = new User(registrationUserDto.getUsername(), passwordEncoder.encode(registrationUserDto.getPassword()), List.of(roleService.getUserRole()));
//        user.setUsername(registrationUserDto.getUsername());
//        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
//        user.setRoles(List.of(roleService.getUserRole()));
//        return userRepository.save(user);
//    }
    public User getUserById(@Min(1) long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException(String.format("User with ID %d not found", userId)));
    }


}

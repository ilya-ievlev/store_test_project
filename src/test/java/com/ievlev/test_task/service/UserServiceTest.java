package com.ievlev.test_task.service;

import com.ievlev.test_task.dto.CustomUserDetailsDto;
import com.ievlev.test_task.exceptions.UserIdNotFoundException;
import com.ievlev.test_task.initializer.IntegrationTestBase;
import com.ievlev.test_task.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class UserServiceTest extends IntegrationTestBase {
    private final UserService userService;


    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void loadByUsernameShouldThrowExceptionIfUsenameNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("asdf"));
    }

    @Test
    public void loadByUsernameShouldReturnUserDetailsWithCorrectFields() {
        CustomUserDetailsDto userDetailsDto = userService.loadUserByUsername("admin");
        assertThat(Long.parseLong(userDetailsDto.getId()) == 2
                && userDetailsDto.getUsername().equals("admin"));
    }

    @Test
    public void getByUsernameShouldReturnRightUser() {
        User user = userService.getByUsername("admin");
        assertThat(user.getId().equals(2)
                && user.getUsername().equals("admin")
                && user.getPassword().equals("$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i"));
    }

    @Test
    public void getByUsernameShouldThrowExceptitonIfUsernameNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.getByUsername("asdf"));
    }

    @Test
    public void getByUsernameShouldThrowExceptionIfUsernameNull() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> userService.getByUsername(null));
    }

    @Test
    public void getUserByIdShouldReturnCorrectUser() {
        User user = userService.getUserById(2);
        assertThat(user.getId().equals(2)
                && user.getUsername().equals("admin")
                && user.getPassword().equals("$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i"));
    }

    @Test
    public void getUserByIdShouldThrowExceptionIfItemNotFound() {
        Assertions.assertThrows(UserIdNotFoundException.class, () -> userService.getUserById(1231244));
    }

    @Test
    public void getUserByIdShouldThrowExceptionIfItemIdLessThanOne() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> userService.getUserById(-1));
    }
}

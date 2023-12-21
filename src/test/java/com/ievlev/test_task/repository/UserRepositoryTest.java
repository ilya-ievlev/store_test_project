package com.ievlev.test_task.repository;

import com.ievlev.test_task.initializer.IntegrationTestBase;
import com.ievlev.test_task.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
@Testcontainers
public class UserRepositoryTest extends IntegrationTestBase {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void findByUsernameShouldReturnCorrectUser(){
        Optional<User> userOptional = userRepository.findByUsername("admin");
        if(userOptional.isPresent()){
            User user = userOptional.get();
            assertThat(user.getId()==2
            && user.getUsername().equals("admin")
            && user.getPassword().equals("$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i")
            && user.getRoles().get(0).getName().equals("ROLE_ADMIN"));
        } else {
            fail("can't find user with username admin");
        }
    }
}

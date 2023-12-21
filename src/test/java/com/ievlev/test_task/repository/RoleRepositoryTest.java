package com.ievlev.test_task.repository;

import com.ievlev.test_task.initializer.IntegrationTestBase;
import com.ievlev.test_task.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
@Testcontainers
public class RoleRepositoryTest extends IntegrationTestBase {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleRepositoryTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    public void findByNameShouldReturnCorrectRole() {
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            assertThat(role.getId() == 1);
        } else {
            fail("can't find role user");
        }
    }
}

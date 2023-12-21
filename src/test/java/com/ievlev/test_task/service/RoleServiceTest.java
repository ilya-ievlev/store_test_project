package com.ievlev.test_task.service;

import com.ievlev.test_task.dto.RoleDto;
import com.ievlev.test_task.exceptions.RoleNotFoundException;
import com.ievlev.test_task.model.Role;
import com.ievlev.test_task.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @InjectMocks
    private RoleService roleService;
    @Mock
    private RoleRepository roleRepository;

    @Test
    public void getUserRoleShouldReturnCorrectUserRole() {
        Optional<Role> userRoleOptional = Optional.of(new Role(1L, "ROLE_USER"));
        Mockito.doReturn(userRoleOptional).when(roleRepository).findByName("ROLE_USER");
        RoleDto roleDtoToCheck = roleService.getUserRole();
        assertThat(roleDtoToCheck.getId().equals(1)
                && roleDtoToCheck.getRoleName().equals("ROLE_USER"));
    }

    @Test
    public void getUserRoleShouldThrowCorrectExceptionIfRoleNotFound() {
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());
        Assertions.assertThrows(RoleNotFoundException.class, () -> roleService.getUserRole());
    }

    @Test
    public void getAdminRoleShouldReturnCorrectAdminRole() {
        Optional<Role> adminRoleOptional = Optional.of(new Role(2L, "ROLE_ADMIN"));
        Mockito.doReturn(adminRoleOptional).when(roleRepository).findByName("ROLE_ADMIN");
        RoleDto roleDtoToCheck = roleService.getAdminRole();
        assertThat(roleDtoToCheck.getId().equals(2)
                && roleDtoToCheck.getRoleName().equals("ROLE_ADMIN"));
    }

    @Test
    public void getAdminRoleShouldThrowCorrectExceptionIfRoleNotFound() {
        Mockito.when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.empty());
        Assertions.assertThrows(RoleNotFoundException.class, () -> roleService.getAdminRole());
    }
}

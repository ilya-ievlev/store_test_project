package com.ievlev.test_task.service;

import com.ievlev.test_task.dto.RoleDto;
import com.ievlev.test_task.exceptions.RoleNotFoundException;
import com.ievlev.test_task.model.Role;
import com.ievlev.test_task.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;


    public RoleDto getUserRole() {
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RoleNotFoundException("can't find user role in db"));
        return new RoleDto(role.getId(), role.getName());
    }

    public RoleDto getAdminRole() {
        Role role = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RoleNotFoundException("can't find admin role in db"));
        return new RoleDto(role.getId(), role.getName());
    }
}

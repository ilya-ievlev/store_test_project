package com.ievlev.test_task.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoleDto {

    @Min(0)
    Long id;
    @NotNull
    @NotBlank
    String roleName;

    public RoleDto(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public RoleDto(String roleName) {
        this.roleName = roleName;
    }
}

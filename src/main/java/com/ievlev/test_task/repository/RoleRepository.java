package com.ievlev.test_task.repository;

import com.ievlev.test_task.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
@Validated
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(@NotNull String name);
}

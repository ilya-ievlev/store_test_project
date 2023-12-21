package com.ievlev.test_task.repository;

import com.ievlev.test_task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
@Validated
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(@NotNull String username);
}

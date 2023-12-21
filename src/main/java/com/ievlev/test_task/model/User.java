package com.ievlev.test_task.model;

import com.ievlev.test_task.validation.constraint.UsernameSymbolsIsCorrectConstraint;
import com.ievlev.test_task.validation.constraint.ValidPasswordConstraint;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Min(1)
    private Long id;

    @Column(name = "username")
    @NotNull
    @Size(min = 1, max = 30)
    @UsernameSymbolsIsCorrectConstraint
    private String username;

    @Column(name = "password")
    @NotNull
    @Size(min = 1, max = 30)
    @ValidPasswordConstraint
    private String password;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @NotNull
    private List<Role> roles;

    @OneToOne(mappedBy = "user")
    private Order order;


    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, String password, List<Role> roles, Order order) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.order = order;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}

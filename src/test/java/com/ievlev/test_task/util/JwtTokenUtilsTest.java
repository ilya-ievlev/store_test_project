package com.ievlev.test_task.util;

import com.ievlev.test_task.dto.CustomUserDetailsDto;
import com.ievlev.test_task.initializer.IntegrationTestBase;
import com.ievlev.test_task.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Testcontainers
public class JwtTokenUtilsTest extends IntegrationTestBase {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    @Autowired
    public JwtTokenUtilsTest(JwtTokenUtils jwtTokenUtils, UserService userService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
    }

    @Value("${jwt.secret}")
    private String secret;

    @Test
    public void generateTokenShouldCreateCorrectAdminRoleInToken() {
        CustomUserDetailsDto userDetailsDto = userService.loadUserByUsername("admin");
        String token = jwtTokenUtils.generateToken(userDetailsDto);
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        List<String> rolesList = claims.get("roles", List.class);
        assertThat(rolesList.contains("ROLE_ADMIN"));
    }

    @Test
    public void generateTokenShouldCreateCorrectUserRoleInToken() {
        CustomUserDetailsDto userDetailsDto = userService.loadUserByUsername("user");
        String token = jwtTokenUtils.generateToken(userDetailsDto);
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        List<String> rolesList = claims.get("roles", List.class);
        assertThat(rolesList.contains("ROLE_USER"));
    }

    @Test
    public void generateTokenShouldPutCorrectSubject() {
        String token = jwtTokenUtils.generateToken(userService.loadUserByUsername("admin"));
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        String subject = claims.getSubject();
        assertThat(subject.equals("admin"));
    }

    @Test
    public void generateTokenShouldThrowExceptionIfUserDetailsIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> jwtTokenUtils.generateToken(null));
    }
}

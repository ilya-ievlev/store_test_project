package com.ievlev.test_task.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;

@UtilityClass
public class UserIdFromAuthenticationUtil {
    public static long getUserIdFromAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("authentication can't be null");
        }
        try {
            return Long.parseLong(authentication.getPrincipal().toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid user ID", e);
        }
    }
}

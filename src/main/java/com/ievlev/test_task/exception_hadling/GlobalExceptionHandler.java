package com.ievlev.test_task.exception_hadling;

import com.ievlev.test_task.dto.AppErrorMapDto;
import com.ievlev.test_task.dto.AppErrorStatusDto;
import com.ievlev.test_task.exceptions.*;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
                    if (errors.containsKey(error.getField())) {
                        errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));
                    } else {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                }
        );
        return new ResponseEntity<>(new AppErrorMapDto(HttpStatus.BAD_REQUEST.value(), errors, "VALIDATION_FAILED"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserIdNotFoundException.class})
    public ResponseEntity<?> handleUserIdNotFoundException(UserIdNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<?> handleItemNotFoundException(ItemNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmptyOrderCannotBePaidException.class})
    public ResponseEntity<?> handleEmptyOrderCannotBePaidException(EmptyOrderCannotBePaidException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.NO_CONTENT.value(), e.getMessage()), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({AddToOrderException.class})
    public ResponseEntity<?> handleAddToOrderException(AddToOrderException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnableToUpdateEntityException.class})
    public ResponseEntity<?> handleUnableToUpdateEntityWithoutIdException(UnableToUpdateEntityException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ObjectDuplicatesInCollectionException.class})
    public ResponseEntity<?> handleObjectDuplicatesInCollection(ObjectDuplicatesInCollectionException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class})
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.UNAUTHORIZED.value(), "authentication exception"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<?> handleBadCredentialsAuthorizeException(BadCredentialsException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.UNAUTHORIZED.value(), "login or password is not correct"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), "role not found"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MalformedJwtException.class})
    public ResponseEntity<?> handleMalformedJwtExcpetion(MalformedJwtException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstrainViolationException(ConstraintViolationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppErrorStatusDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

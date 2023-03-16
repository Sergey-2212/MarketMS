package ru.gb.authservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> catchUsernameNotFoundException (UsernameNotFoundException e) {
        log.error("catchUsernameNotFoundException " + e.getMessage());
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<AppError> catchUserIsAlreadyExistException (UserIsAlreadyExistException e) {
        log.error("catchUserIsAlreadyExistException " + e.getMessage());
        return new ResponseEntity<>(new AppError(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), e.getMessage()), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }
    @ExceptionHandler
    public ResponseEntity<AppError> catchPasswordsDontMatchException (PasswordsDontMatchException e) {
        log.error("catchPasswordsDontMatchException " + e.getMessage());
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }


}

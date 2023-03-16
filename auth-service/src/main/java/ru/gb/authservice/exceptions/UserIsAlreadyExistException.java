package ru.gb.authservice.exceptions;

public class UserIsAlreadyExistException extends RuntimeException {
    public UserIsAlreadyExistException(String message) {
        super(message);
    }
}

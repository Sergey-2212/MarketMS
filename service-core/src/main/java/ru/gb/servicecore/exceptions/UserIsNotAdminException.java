package ru.gb.servicecore.exceptions;

public class UserIsNotAdminException extends RuntimeException {

    public UserIsNotAdminException(String message) {
        super(message);
    }
}

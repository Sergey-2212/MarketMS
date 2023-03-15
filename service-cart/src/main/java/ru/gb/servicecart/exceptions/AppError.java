package ru.gb.servicecart.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppError {

    private Integer status;
    private String message;

    public AppError(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

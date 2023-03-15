package ru.gb.servicecore.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AppError {

    private Integer status;
    private String message;

    public AppError(Integer status, String message) {

        this.status = status;
        this.message = message;
    }
}

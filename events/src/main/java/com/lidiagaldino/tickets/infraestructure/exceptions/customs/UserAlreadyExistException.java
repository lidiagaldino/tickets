package com.lidiagaldino.tickets.infraestructure.exceptions.customs;

import com.lidiagaldino.tickets.infraestructure.exceptions.BaseCustomException;

public class UserAlreadyExistException extends BaseCustomException {

    public static final String DEFAULT_MESSAGE = "User is already exist";
    public static final String ERROR_MESSAGE = "Email is already taken";
    public static final Integer ERROR_CODE = 409;

    public UserAlreadyExistException() {
        super(DEFAULT_MESSAGE, ERROR_MESSAGE, ERROR_CODE);
    }
}

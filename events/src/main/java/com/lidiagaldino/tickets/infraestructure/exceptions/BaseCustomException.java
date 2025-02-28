package com.lidiagaldino.tickets.infraestructure.exceptions;

public class BaseCustomException extends RuntimeException implements CustomException{

    protected final String errorCode;
    protected final Integer httpStatusCode;

    public BaseCustomException(String message, String errorCode, Integer httpStatusCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public Integer getHttpStatusCode() {
        return this.httpStatusCode;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }
}

package com.jay.customer.exception;

import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException {

    private ExceptionType exceptionType;

    private String field;

    public CustomerException(ExceptionType exceptionType, String field) {
        super(exceptionType.getErrorMessage());
        this.exceptionType = exceptionType;
        this.field = field;
    }

    public CustomerException(ExceptionType exceptionType,Throwable cause, String field) {
        super(exceptionType.getErrorMessage(),cause);
        this.exceptionType = exceptionType;
        this.field = field;
    }

}

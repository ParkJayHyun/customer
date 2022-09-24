package com.jay.customer.exception;

import org.springframework.stereotype.Component;

@Component
public interface ExceptionType {
    int getStatus();
    String getErrorCode();
    String getErrorMessage();
    String getFieldErrorMessage();
}

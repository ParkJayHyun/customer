package com.jay.customer.advice;

import com.jay.customer.dto.CustomerResponse;
import com.jay.customer.error.Errors;
import com.jay.customer.error.exception.CustomerException;
import com.jay.customer.error.exception.CustomerExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomerControllerAdvice {

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<CustomerResponse> customerException(CustomerException exception) {
        log.error("[CustomerException ERROR] Error Message = {}",exception.getMessage(),exception.getCause());
        Errors errors = Errors.create(exception.getExceptionType(), exception.getField());
        CustomerResponse response = CustomerResponse.createFail(exception.getExceptionType().getStatus(), "FAIL" ,errors);
        return new ResponseEntity(response, new HttpHeaders(), exception.getExceptionType().getStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CustomerResponse> customerBindingException(BindException exception) {
        Errors errors = Errors.create(CustomerExceptionType.INVALID_FIELD, exception.getBindingResult());
        CustomerResponse response = CustomerResponse.createFail(CustomerExceptionType.INVALID_FIELD.getStatus(), "FAIL", errors);
        return new ResponseEntity(response, new HttpHeaders(), CustomerExceptionType.INVALID_FIELD.getStatus());

    }
}

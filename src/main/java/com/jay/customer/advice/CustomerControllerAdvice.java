package com.jay.customer.advice;

import com.jay.customer.dto.CustomerResponse;
import com.jay.customer.exception.error.Errors;
import com.jay.customer.exception.CustomerException;
import com.jay.customer.exception.CustomerExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<CustomerResponse> duplicateUserIdException(DataAccessException exception) {
        log.error("[DuplicateUserIdException ERROR] Error Message = {}",exception.getMessage(),exception.getCause());
        DataIntegrityViolationException violationException = (DataIntegrityViolationException) exception;
        Errors errors = Errors.create(CustomerExceptionType.EXIST_CUSTOMER, "userId");
        CustomerResponse response = CustomerResponse.createFail(CustomerExceptionType.EXIST_CUSTOMER.getStatus(), "FAIL", errors);
        return new ResponseEntity(response, new HttpHeaders(), CustomerExceptionType.EXIST_CUSTOMER.getStatus());

    }
}

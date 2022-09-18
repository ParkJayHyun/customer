package com.jay.customer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerException extends RuntimeException {

    private List<Error> errors = new ArrayList<>();

    public List<Error> getErrors() {
        return errors;
    }

    public CustomerException(String defaultMessage, String field) {
        super(defaultMessage);
        this.errors.add(new Error(defaultMessage,field));
    }

    @Getter
    static class Error{
        private String defaultMessage;
        private String field;

        public Error(String defaultMessage, String field) {
            this.defaultMessage = defaultMessage;
            this.field = field;
        }
    }

}

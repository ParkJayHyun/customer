package com.jay.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jay.customer.error.Errors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {

    private int code;

    private String result;

    private CustomerDto customer;

    private Errors errors;

    private CustomerResponse(int code, String result, CustomerDto customer) {
        this.code = code;
        this.result = result;
        this.customer = customer;
    }

    private CustomerResponse(int code, String result, Errors errors) {
        this.code = code;
        this.result = result;
        this.errors = errors;
    }

    public static CustomerResponse createSuccess(int status, String result, CustomerDto customer){
        return new CustomerResponse(status, result, customer);
    }

    public static CustomerResponse createFail(int status, String result, Errors errors){
        return new CustomerResponse(status, result, errors);
    }


}

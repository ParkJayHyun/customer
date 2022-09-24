package com.jay.customer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomerExceptionType implements ExceptionType{

    INVALID_FIELD(HttpStatus.BAD_REQUEST.value(), "U001", "Invalid Field"),
    EXIST_CUSTOMER(HttpStatus.BAD_REQUEST.value(),"U002","Already Exist Customer","이미 사용중인 아이디 입니다."),
    NOT_EXIST_CUSTOMER(HttpStatus.BAD_REQUEST.value(),"U003","Not Exist Customer","존재하지 않는 회원입니다."),
    SIGNUP_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "U004","SignUp Internal Server Error","회원가입 서비스에 불편을 드려 죄송합니다. 고객센터에 문의해주세요.");


    private int status;
    private String errorCode;
    private String errorMessage;
    private String fieldErrorMessage;

    CustomerExceptionType(int status,String errorCode, String errorMessage) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    CustomerExceptionType(int status,String errorCode, String errorMessage,String fieldErrorMessage) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.fieldErrorMessage = fieldErrorMessage;
    }

}

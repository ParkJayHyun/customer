package com.jay.customer.error;

import com.jay.customer.error.exception.ExceptionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Errors {

    private String code;                                      //ErrorCode
    private String message;                                   //ErrorMessage
    private List<FieldErrors> fields = new ArrayList<>();


    private Errors(ExceptionType exceptionType, List<FieldErrors> errors){
        this.code = exceptionType.getErrorCode();
        this.message = exceptionType.getErrorMessage();
        this.fields = errors;
    }

    public static Errors create(ExceptionType exceptionType,String field) {
        return new Errors(exceptionType, FieldErrors.fieldErrors(new FieldErrors(field,exceptionType.getFieldErrorMessage())));
    }

    public static Errors create(ExceptionType exceptionType, BindingResult bindingResult) {
        return new Errors(exceptionType, FieldErrors.fieldErrorList(bindingResult));
    }


    @Getter
    static class FieldErrors {
        private String name;
        private String reason;

        public FieldErrors(String name, String reason) {
            this.name = name;
            this.reason = reason;
        }
        public static List<FieldErrors> fieldErrorList(BindingResult result) {
            List<FieldError> errorList = result.getFieldErrors();
            List<FieldErrors> fieldErrorsList = new ArrayList<>();
            for (FieldError fieldError : errorList) {
                fieldErrorsList.add(new FieldErrors(fieldError.getField(),
                        fieldError.getDefaultMessage()));
            }
            return fieldErrorsList;
        }

        public static List<FieldErrors> fieldErrors(FieldErrors error) {
            List<FieldErrors> fieldErrorsList = new ArrayList<>();
            fieldErrorsList.add(error);
            return fieldErrorsList;
        }
    }
}

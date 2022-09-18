package com.jay.customer.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password,String> {

    private static final String PATTERN_1 =".*[0-9]+.*$";              //숫자
    private static final String PATTERN_2 =".*[a-zA-Z]+.*$";           //영문자
    private static final String PATTERN_3 =".*[!@#$%^&+=]+.*$";        //특스문자
    private static final Integer PASSWORD_LENGTH_MIN  = 8;             //비밀번호 최소길이
    private static final Integer PASSWORD_LENGTH_MAX  = 15;            //비밀번호 최대길이
    private static final Integer PASSWORD_COMBINATION_COUNT  = 2;      //비밀번호 패턴 조합 합

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        //입력값이 존재하지 않을 경우
        if(!StringUtils.hasText(password)){
            return false;
        }
        
        //비밀번호 길이 체크
        if(password.length() < PASSWORD_LENGTH_MIN && password.length() > PASSWORD_LENGTH_MAX){
            addConstraintViolation(context, String.format("비밀번호는 최소%d자 ~ 최대 %d자내 입력해 주세요.",PASSWORD_LENGTH_MIN,PASSWORD_LENGTH_MAX));
            return false;
        }

        //비밀번호는 영문자 ,숫자 , 특수문자 2가지 이상 조합
        int count = 0;
        if(password.matches(PATTERN_1)){
            count++;
        }
        if(password.matches(PATTERN_2)){
            count++;
        }
        if(password.matches(PATTERN_3)){
            count++;
        }
        if(count < PASSWORD_COMBINATION_COUNT ){
            addConstraintViolation(context, String.format("영문대소문자, 숫자, 특수문자 %d가지 이상 포함되게 입력해 주세요.",PASSWORD_COMBINATION_COUNT));
            return false;
        }
        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String msg) {
        //기본 메시지 비활성화
        context.disableDefaultConstraintViolation();
        //새로운 메시지 추가
        context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
    }
}

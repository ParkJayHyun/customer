package com.jay.customer.dto;

import com.jay.customer.validator.Password;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.stream.Collectors;

@Setter
@Getter
public class CustomerRequest {

    @Length(min = 5,max = 20 ,message = "아이디는 최소5자, 최대 20자내에 입력해 주세요.")
    @Pattern(regexp = "^[a-z0-9]*$" ,message = "영문소문자, 숫자만 입력 가능합니다.")
    private String userId;

    @Password
    private String password;

    @Email
    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$",message = "휴대폰 형식에 맞춰서 입력해 주세요.")
    private String phoneNumber;

    private String parsePhoneNumber() {
        String phone = Arrays.stream(phoneNumber.split("-")).collect(Collectors.joining());

        int mid = phone.length() == 10 ? 6 : 7;
        return phone.substring(0, 3) + phone.substring(3, mid) + phone.substring(mid);
    }

    public String usePhoneNumber() {
        return parsePhoneNumber();
    }

}

package com.jay.customer.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Setter
@Getter
public class CustomerResponseDto {

    private String userId;

    private String email;

    private String phoneNumber;

    public String getPhoneNumber() {
        int mid = phoneNumber.length() == 10 ? 6 : 7;
        return phoneNumber.substring(0, 3) +"-"+ phoneNumber.substring(3, mid) +"-"+phoneNumber.substring(mid);
    }
}

package com.jay.customer.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerDto {

    private String userId;

    private String email;

    private String phoneNumber;

    private String parsePhoneNumber() {
        int mid = phoneNumber.length() == 10 ? 6 : 7;
        return phoneNumber.substring(0, 3) +"-"+ phoneNumber.substring(3, mid) +"-"+phoneNumber.substring(mid);
    }

    public String getPhoneNumber() {
        return parsePhoneNumber();
    }

}

package com.jay.customer.controller;

import com.jay.customer.common.utils.EncryptorUtils;
import com.jay.customer.domain.Customer;
import com.jay.customer.dto.CustomerRequestDto;
import com.jay.customer.dto.CustomerResponseDto;
import com.jay.customer.exception.CustomerException;
import com.jay.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerApiController {

    private final CustomerService service;

    @PostMapping("/signUp")
    public Integer signUp(@RequestBody @Valid CustomerRequestDto dto) {
        Customer customer = transRequestDtoToCustomer(dto);
        return service.save(customer);
    }

    @GetMapping("/{id}")
    public CustomerResponseDto customer(@PathVariable Integer id) {
        Customer customer = service.findById(id);
        return transCustomerToResponseDto(customer);
    }


    private CustomerResponseDto transCustomerToResponseDto(Customer customer){
        return decryptCustomer(customer);
    }

    private Customer transRequestDtoToCustomer(CustomerRequestDto form) {
        Customer customer = Customer.builder()
                .userId(form.getUserId())
                .password(form.getPassword())
                .email(form.getEmail())
                .phoneNumber(form.usePhoneNumber())
                .build();
        return encryptCustomer(customer);
    }

    private Customer encryptCustomer(Customer customer){
        try {
            return Customer.builder()
                    .userId(EncryptorUtils.CommonEncrypt(customer.getUserId()))
                    .password(EncryptorUtils.passwordEncrypt(customer.getPassword()))
                    .email(EncryptorUtils.CommonEncrypt(customer.getEmail()))
                    .phoneNumber(EncryptorUtils.CommonEncrypt(customer.getPhoneNumber()))
                    .build();
        } catch (Exception e) {
            log.error("encryptCustomer error", e);
            throw new CustomerException("회원가입 서비스에 불편을 드려 죄송합니다. 고객센터에 문의해주세요.", "globalErrors");
        }
    }

    private CustomerResponseDto decryptCustomer(Customer customer){
        try {
            CustomerResponseDto responseDto = new CustomerResponseDto();
            responseDto.setUserId(EncryptorUtils.CommonDecrypt(customer.getUserId()));
            responseDto.setPhoneNumber(EncryptorUtils.CommonDecrypt(customer.getPhoneNumber()));
            responseDto.setEmail(EncryptorUtils.CommonDecrypt(customer.getEmail()));
            return responseDto;
        } catch (Exception e) {
            log.error("decryptCustomer error", e);
            throw new CustomerException("회원조회 서비스에 불편을 드려 죄송합니다. 고객센터에 문의해주세요.", "globalErrors");
        }
    }

}

package com.jay.customer.controller;

import com.jay.customer.domain.Customer;
import com.jay.customer.dto.CustomerRequestDto;
import com.jay.customer.dto.CustomerResponseDto;
import com.jay.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerApiController {

    private final CustomerService service;

    @PostMapping("/signUp")
    public Integer signUp(@RequestBody @Valid CustomerRequestDto dto) {
        Customer customer = transFormTooCustomer(dto);
        return service.save(customer);
    }

    @GetMapping("/{id}")
    public CustomerResponseDto customer(@PathVariable Integer id) {
        Customer customer = service.findById(id);
        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setUserId(customer.getUserId());
        responseDto.setPhoneNumber(customer.getPhoneNumber());
        responseDto.setEmail(customer.getEmail());

        return responseDto;
    }


    private Customer transFormTooCustomer(CustomerRequestDto form) {
        return Customer.builder()
                .userId(form.getUserId())
                .password(form.getPassword())
                .email(form.getEmail())
                .phoneNumber(form.usePhoneNumber())
                .build();
    }

}

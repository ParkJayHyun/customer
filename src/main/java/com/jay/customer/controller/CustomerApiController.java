package com.jay.customer.controller;

import com.jay.customer.common.utils.EncryptorUtils;
import com.jay.customer.domain.Customer;
import com.jay.customer.dto.CustomerDto;
import com.jay.customer.dto.CustomerRequest;
import com.jay.customer.dto.CustomerResponse;
import com.jay.customer.exception.CustomerException;
import com.jay.customer.exception.CustomerExceptionType;
import com.jay.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerApiController {

    private final CustomerService service;

    /**
     * 회원가입
     * @param request
     * @return
     */
    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody @Valid CustomerRequest request) {
        Customer customer = transRequestToCustomer(request);
        Integer customerId = service.save(customer);
        Map<String, Object> result = new HashMap<>();
        result.put("code", HttpStatus.CREATED.value());
        result.put("result", "SUCCESS");
        result.put("id", customerId);
        return new ResponseEntity(result, new HttpHeaders(), HttpStatus.CREATED.value());
    }

    /**
     * 회원 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CustomerResponse customer(@PathVariable Integer id) {
        Customer customer = service.findById(id);
        CustomerDto customerDto = transCustomerToCustomerDto(customer);
        return CustomerResponse.createSuccess(HttpStatus.OK.value(), "SUCCESS",customerDto);
    }


    private CustomerDto transCustomerToCustomerDto(Customer customer) {
        return decryptCustomer(customer);
    }

    private Customer transRequestToCustomer(CustomerRequest request) {
        return encryptCustomer(request);
    }

    private Customer encryptCustomer(CustomerRequest request) {
        try {
            return Customer.builder()
                    .userId(EncryptorUtils.CommonEncrypt(request.getUserId()))
                    .password(EncryptorUtils.passwordEncrypt(request.getPassword()))
                    .email(EncryptorUtils.CommonEncrypt(request.getEmail()))
                    .phoneNumber(EncryptorUtils.CommonEncrypt(request.usePhoneNumber()))
                    .build();
        } catch (Exception e) {
            throw new CustomerException(CustomerExceptionType.SIGNUP_INTERNAL_SERVER_ERROR, e, "globalErrors");
        }
    }

    private CustomerDto decryptCustomer(Customer customer) {
        try {
            return CustomerDto.builder()
                    .userId(EncryptorUtils.CommonDecrypt(customer.getUserId()))
                    .phoneNumber(EncryptorUtils.CommonDecrypt(customer.getPhoneNumber()))
                    .email(EncryptorUtils.CommonDecrypt(customer.getEmail()))
                    .build();
        } catch (Exception e) {
            throw new CustomerException(CustomerExceptionType.SIGNUP_INTERNAL_SERVER_ERROR, e, "globalErrors");
        }
    }

}

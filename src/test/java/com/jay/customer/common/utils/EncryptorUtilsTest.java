package com.jay.customer.common.utils;

import com.jay.customer.domain.Customer;
import com.jay.customer.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EncryptorUtilsTest {

    @Autowired
    CustomerService service;

    @Test
    @Transactional
    void shouldEncrypt() throws Exception {

        //given
        Customer customer = Customer.builder()
                .userId("test1")
                .password("customer1")
                .email("test1@naver.com")
                .phoneNumber("01012345678")
                .build();

        Customer encryptCustomer = Customer.builder()
                .userId(EncryptorUtils.CommonEncrypt(customer.getUserId()))
                .password(EncryptorUtils.passwordEncrypt(customer.getPassword()))
                .email(EncryptorUtils.CommonEncrypt(customer.getEmail()))
                .phoneNumber(EncryptorUtils.CommonEncrypt(customer.getPhoneNumber()))
                .build();

        //when
        Integer saveId = service.save(encryptCustomer);
        Customer findCustomer = service.findById(saveId);

        //then
        Assertions.assertThat(encryptCustomer.getUserId()).isEqualTo(findCustomer.getUserId());
        Assertions.assertThat(encryptCustomer.getPassword()).isEqualTo(findCustomer.getPassword());
        Assertions.assertThat(encryptCustomer.getEmail()).isEqualTo(findCustomer.getEmail());
        Assertions.assertThat(encryptCustomer.getPhoneNumber()).isEqualTo(findCustomer.getPhoneNumber());

    }

    @Test
    @Transactional
    void shouldDecrypt() throws Exception {

        //given
        Customer customer = Customer.builder()
                .userId("test1")
                .email("test1@naver.com")
                .phoneNumber("01012345678")
                .build();
        Customer encryptCustomer = Customer.builder()
                .userId(EncryptorUtils.CommonEncrypt(customer.getUserId()))
                .email(EncryptorUtils.CommonEncrypt(customer.getEmail()))
                .phoneNumber(EncryptorUtils.CommonEncrypt(customer.getPhoneNumber()))
                .build();

        //when
        Integer saveId = service.save(encryptCustomer);
        Customer findCustomer = service.findById(saveId);

        Customer decryptCustomer = Customer.builder()
                .userId(EncryptorUtils.CommonDecrypt(findCustomer.getUserId()))
                .email(EncryptorUtils.CommonDecrypt(findCustomer.getEmail()))
                .phoneNumber(EncryptorUtils.CommonDecrypt(findCustomer.getPhoneNumber()))
                .build();

        //then
        Assertions.assertThat(decryptCustomer.getUserId()).isEqualTo(customer.getUserId());
        Assertions.assertThat(decryptCustomer.getEmail()).isEqualTo(customer.getEmail());
        Assertions.assertThat(decryptCustomer.getPhoneNumber()).isEqualTo(customer.getPhoneNumber());
    }
}
package com.jay.customer.service;

import com.jay.customer.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CustomerServiceTest {

    @Autowired
    CustomerService service;

    @Test
    @DisplayName("회원 등록")
    @Transactional
    void save() {
        //given
        Customer customer = Customer.builder()
                .userId("jay")
                .password("qkrwogus")
                .email("kokikiko@naver.com")
                .phoneNumber("01022141242")
                .build();

        service.save(customer);

        //when
        Customer findCustomer = service.findById(customer.getId());
        log.info("findCustomer.getId = {}", customer.getId());

        //then
        assertThat(customer).isEqualTo(findCustomer);
    }

    @Test
    @DisplayName("회원 조회")
    void findCustomer() {
        //given
        Customer customer = Customer.builder()
                .userId("jay")
                .password("qkrwogus")
                .email("kokikiko@naver.com")
                .phoneNumber("01022141242")
                .build();
        service.save(customer);

        //when
        Customer findCustomer = service.findById(customer.getId());

        //then
        assertThat(findCustomer).isNotNull();
    }
    
    
    
}
package com.jay.customer.service;

import com.jay.customer.domain.Customer;
import com.jay.customer.error.exception.CustomerException;
import com.jay.customer.error.exception.CustomerExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    @DisplayName("동시성 테스트")
    @Rollback(value = false)
    void concurrent() throws InterruptedException {
        //given
        Customer customerA = Customer.builder()
                .userId("customer")
                .password("qkrwogus1")
                .email("customer@naver.com")
                .phoneNumber("01011112222")
                .build();

        Customer customerB = Customer.builder()
                .userId("customer")
                .password("qkrwogus1")
                .email("customer@naver.com")
                .phoneNumber("01011112222")
                .build();
        //when
        Thread threadCustomerA = new Thread(new Runnable() {
            @Override
            public void run() {
                service.save(customerA);
            }
        });

        Thread threadCustomerB = new Thread(new Runnable() {
            @Override
            public void run() {
                service.save(customerB);
            }
        });

        //then
        threadCustomerA.start();
        Thread.sleep(200);
        threadCustomerB.start();

        Thread.sleep(1000);
        /*assertThatThrownBy(() -> threadCustomerB.start())
                .isInstanceOf(CustomerException.class)
                .hasMessage("Already Exist Customer");*/
        /*org.junit.jupiter.api.Assertions.assertThrows(CustomerException.class,
                () -> threadCustomerB.start(),
                "Already Exist Customer"
        );*/


    }

    
}
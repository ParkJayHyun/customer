package com.jay.customer.repository;

import com.jay.customer.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryImplTest {

    @Autowired
    CustomerRepository repository;

    @Test
    @Transactional
    @DisplayName("회원 등록")
    void save() {
        //given
        Customer customer = new Customer("jay", "qkrwogus", "kokikiko@naver.com", "01022141242");
        repository.save(customer);

        //when
        Customer findCustomer = repository.findById(customer.getId()).get();

        //then
        assertThat(customer).isEqualTo(findCustomer);
    }

    @Test
    @Transactional
    @DisplayName("회원 조회")
    void findCustomer() {
        //given
        Customer customer = new Customer("jay", "qkrwogus", "kokikiko@naver.com", "01022141242");
        repository.save(customer);

        //when
        Customer findCustomer = repository.findById(customer.getId()).get();

        //then
        assertThat(findCustomer).isNotNull();
        
    }


}
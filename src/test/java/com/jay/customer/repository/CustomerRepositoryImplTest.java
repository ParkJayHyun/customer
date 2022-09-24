package com.jay.customer.repository;

import com.jay.customer.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class CustomerRepositoryImplTest {

    @Autowired
    CustomerRepository repository;
    
    // 멀티 쓰레드로 동시성 테스트 시 일부 데이터가 롤백되지 않는 현상 발생 
    // 각 쓰레드별 다른 커넥션을 사용하여 전체 롤백이 되지 않는 것으로 판단되어
    // 전체 테스트 실행 시 다른 테스트 케이스에 영향을 끼치지 않도록 사용
    @AfterTransaction
    void initDb() {
        repository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("회원 등록")
    void save() {
        //given
        Customer customer = Customer.builder()
                .userId("customer")
                .password("customer1")
                .email("customer@naver.com")
                .phoneNumber("01011112222")
                .build();
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
        Customer customer = Customer.builder()
                .userId("customer")
                .password("customer1")
                .email("customer@naver.com")
                .phoneNumber("01011112222")
                .build();
        repository.save(customer);

        //when
        Customer findCustomer = repository.findById(customer.getId()).get();

        //then
        assertThat(findCustomer).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("회원 동시성 테스트")
    void concurrent() throws InterruptedException {
        //given
        Customer customer = Customer.builder()
                .userId("customer")
                .password("customer1")
                .email("customer@naver.com")
                .phoneNumber("01011112222")
                .build();

        //when
        int threadCount = 2;
        AtomicInteger duplicateUserIdCount = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    repository.save(customer);
                } catch (DataAccessException e) {
                    duplicateUserIdCount.addAndGet(1);
                } finally {
                    latch.countDown();
                }
            });
        }

        //then
        latch.await();
        assertThat(duplicateUserIdCount.get()).isEqualTo(1);
    }


}

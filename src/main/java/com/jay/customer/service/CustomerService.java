package com.jay.customer.service;

import com.jay.customer.domain.Customer;
import com.jay.customer.exception.CustomerException;
import com.jay.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    @Transactional
    public Integer save(Customer customer) {
        verifyDuplicateUserId(customer.getUserId());
        return repository.save(customer).getId();
    }

    public Customer findById(Integer id) {
        Optional<Customer> findCustomer = repository.findById(id);
        if(!findCustomer.isPresent()){
            throw new CustomerException("존재하지 않는 회원입니다.", "id");
        }
        return findCustomer.get();
    }

    public void verifyDuplicateUserId(String userId) {
        Optional<Customer> customer = repository.findByUserId(userId);
        if(customer.isPresent()){
            log.info(" customer.get().getId() = {}" , customer.get().getId());
            throw new CustomerException("이미 사용중인 아이디 입니다.","userId");
        }
    }


}

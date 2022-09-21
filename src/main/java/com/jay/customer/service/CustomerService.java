package com.jay.customer.service;

import com.jay.customer.domain.Customer;
import com.jay.customer.error.exception.CustomerException;
import com.jay.customer.error.exception.CustomerExceptionType;
import com.jay.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository repository;

    public Integer save(Customer customer) {
        verifyDuplicateUserId(customer.getUserId());
        return repository.save(customer).getId();
    }

    @Transactional(readOnly = true)
    public Customer findById(Integer id) {
        Optional<Customer> findCustomer = repository.findById(id);
        if(!findCustomer.isPresent()){
            throw new CustomerException(CustomerExceptionType.NOT_EXIST_CUSTOMER, "id");
        }
        return findCustomer.get();
    }

    @Transactional(readOnly = true)
    public void verifyDuplicateUserId(String userId) {
        Optional<Customer> customer = repository.findByUserId(userId);
        if(customer.isPresent()){
            throw new CustomerException(CustomerExceptionType.EXIST_CUSTOMER,"userId");
        }
    }

}

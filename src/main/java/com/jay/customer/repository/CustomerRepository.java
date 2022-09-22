package com.jay.customer.repository;

import com.jay.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select c from Customer c where c.userId = :userId")
    Optional<Customer> findByUserId(String userId);
}

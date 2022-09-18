package com.jay.customer.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerExceptionTest {


    @Test
    void createException() {
        CustomerException exception;

        exception = new CustomerException("test1", "11");
        exception = new CustomerException("test2", "12");
        exception = new CustomerException("test3", "13");
        exception = new CustomerException("test4", "14");


    }

}
package com.jay.customer.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String userId;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Version
    private Long version;

    @Builder
    public Customer(String userId, String password, String email, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}

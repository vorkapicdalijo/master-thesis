package com.fer.hr.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private Integer zipCode;
    private String country;
}

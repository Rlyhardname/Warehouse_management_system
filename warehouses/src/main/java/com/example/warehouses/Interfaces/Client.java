package com.example.warehouses.Interfaces;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table
@Entity
public abstract class Client {

    @jakarta.persistence.Id
    private Long Id;
    private java.lang.String email;
    private java.lang.String password;
    private java.lang.String firstName;
    private java.lang.String lastName;
    private java.lang.String accountType;

}

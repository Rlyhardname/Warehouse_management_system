package com.example.warehouses.Interfaces;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public abstract class Client{

    @jakarta.persistence.Id
    private Long Id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String accountType;

}

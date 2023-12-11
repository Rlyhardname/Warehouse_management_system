package com.example.warehouses.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@Entity
@EqualsAndHashCode
@DiscriminatorValue("owner")
public class Owner extends User {

    public Owner(String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
    }

    public Owner() {
    }
}
package com.example.warehouses.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("agent")
public class Agent extends User {

    public Agent(String email, String password, String firstName, String lastName) {
        super(email, password, firstName, lastName);
    }

    public Agent() {
    }

}

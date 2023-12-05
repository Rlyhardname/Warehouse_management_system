package com.example.warehouses.Model.User;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("agent")
public class Agent extends User {

    public void init(String email, String password, String firstName, String lastName) {
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }

}

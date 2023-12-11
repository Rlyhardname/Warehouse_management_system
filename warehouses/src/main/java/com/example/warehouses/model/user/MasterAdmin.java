package com.example.warehouses.model.user;

import com.example.warehouses.interfaces.Administrator;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class MasterAdmin extends Administrator {

    public void init(String email, String password, String firstName, String lastName) {
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);

    }

    public UserImpl createUser(String email,
                               String password,
                               String firstName,
                               String lastName,
                               String clientType) {
        return UserFactory.instanceOf(clientType, email, password, firstName, lastName);
    }

}
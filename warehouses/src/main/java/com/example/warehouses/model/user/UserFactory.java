package com.example.warehouses.model.user;

import com.example.warehouses.exception.user.UserTypeCreationError;

public class UserFactory {

    public static UserImpl instanceOf(String type, String email, String password, String firstName, String lastName) {

        if (type.toLowerCase().equals("owner")) {
            return new Owner(email, password, firstName, lastName);
        } else if (type.toLowerCase().equals("agent")) {
            return new Agent(email, password, firstName, lastName);
        }

        throw new UserTypeCreationError();
    }

}

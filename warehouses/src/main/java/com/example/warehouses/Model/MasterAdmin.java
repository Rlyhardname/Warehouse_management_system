package com.example.warehouses.Model;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.Client;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Entity
public class MasterAdmin extends Administrator{

    public void init(String email, String password, String firstName, String lastName){
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }

}
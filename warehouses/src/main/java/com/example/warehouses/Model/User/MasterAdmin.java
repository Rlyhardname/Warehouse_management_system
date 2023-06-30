package com.example.warehouses.Model.User;

import com.example.warehouses.Interfaces.Administrator;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class MasterAdmin extends Administrator{

    private String yoyo;
    public void init(String email, String password, String firstName, String lastName){
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        yoyo = "0";

    }

    public void createOwner(){

    }

}
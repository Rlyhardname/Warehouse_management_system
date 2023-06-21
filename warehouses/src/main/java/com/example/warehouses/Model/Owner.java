package com.example.warehouses.Model;

import com.example.warehouses.Interfaces.Client;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@Entity
@RequiredArgsConstructor
public class Owner extends Client {


    private Map<Long,String> warehousesOwned;

    public void init(String email, String password, String firstName, String lastName){
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        warehousesOwned = new TreeMap<Long,String>();

    }

    public void CreatedWarehouse(Long id, String warehouseName) {
        try {
            warehousesOwned.put(id,warehouseName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void AddAgent(Agent agent) {

        try {
         //   repository.addAgent(agent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
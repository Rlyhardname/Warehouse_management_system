package com.example.warehouses.Model;

import com.example.warehouses.Interfaces.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Setter
@Getter
@ToString
@Entity
@RequiredArgsConstructor
public class Owner extends Client {

   //@JsonManagedReference
   @JsonIgnore
   @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="owner")
   private Set<Warehouse> warehousesOwned;

    public void init(String email, String password, String firstName, String lastName){
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        warehousesOwned = new HashSet<>();
    }

    public void CreatedWarehouse(Warehouse warehouse) {
            warehousesOwned.add(warehouse);
    }


    public void AddAgent(Agent agent) {

        try {
         //   repository.addAgent(agent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
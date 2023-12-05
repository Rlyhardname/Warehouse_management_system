package com.example.warehouses.Model.User;

import com.example.warehouses.Configurations.Enum.WarehouseCategory;
import com.example.warehouses.Model.AgentRatings;
import com.example.warehouses.Model.AgentRatingsPK;
import com.example.warehouses.Model.warehouse.Address;
import com.example.warehouses.Model.warehouse.Warehouse;
import com.example.warehouses.Model.warehouse.WarehouseAsignedToAgentPK;
import com.example.warehouses.Model.warehouse.WarehouseAssignedToAgent;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@RequiredArgsConstructor
@DiscriminatorValue("owner")
public class Owner extends User {

    public void init(String email, String password, String firstName, String lastName) {
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }

}
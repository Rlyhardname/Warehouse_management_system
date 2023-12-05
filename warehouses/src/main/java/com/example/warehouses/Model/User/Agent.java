package com.example.warehouses.Model.User;

import com.example.warehouses.Exception.Warehouse.WarehouseNotExistingException;
import com.example.warehouses.Model.AgentRatings;
import com.example.warehouses.Model.Notification;
import com.example.warehouses.Model.warehouse.RentalForm;
import com.example.warehouses.Model.warehouse.Warehouse;
import com.example.warehouses.Model.warehouse.WarehouseAssignedToAgent;
import com.example.warehouses.Repository.WarehouseRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("agent")
public class Agent extends Client {

    public void init(String email, String password, String firstName, String lastName) {
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }

}

package com.example.warehouses.Model;

import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Repository.WarehouseRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.HashMap;
@Data
@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Agent extends Client {

//    private HashMap<Integer, Integer> ratings; // rating ID, rating Owner, Rating value 1-5 - Class RatingsReceived
//    private HashMap<Integer, Warehouse> controlledWarehouses; // Id, warehouseId - Class ControlledWarehouses

    public void receiveRating(int ownerId, int rating) {

//        try {
//            ratings.put(ownerId, rating);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public void rentWarehouse(WarehouseRepository repository, RentReceipt receipt, Long warehouseId) {

        try {
            Warehouse warehouse = repository.findById(warehouseId).orElseThrow(() -> new IllegalStateException("Warehouse " + warehouseId + "doesn't exist"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init(String email, String password, String firstName, String lastName) {
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }
}

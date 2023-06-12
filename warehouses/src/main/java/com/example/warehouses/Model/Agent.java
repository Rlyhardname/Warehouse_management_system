package com.example.warehouses.Model;

import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Repository.WarehouseRepository;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent extends Client {

    @Id
    private Long Id;
    private java.lang.String firstName;
    private java.lang.String lastName;
    private HashMap<Integer, Integer> ratings; // rating ID, rating Owner, Rating value 1-5 - Class RatingsReceived
    private HashMap<Integer, Warehouse> controlledWarehouses; // Id, warehouseId - Class ControlledWarehouses

    public void receiveRating(int ownerId, int rating) {

        try {
            ratings.put(ownerId, rating);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void rentWarehouse(WarehouseRepository repository, RentReceipt receipt, Long warehouseId) {

        try {
            Warehouse warehouse = repository.findById(warehouseId).orElseThrow(() -> new IllegalStateException("Warehouse " + warehouseId + "doesn't exist"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

package com.example.warehouses.Model;

import com.example.warehouses.Interfaces.Client;

public class Owner {

    public void CreatedWarehouse(Warehouse warehouse) {
        try {
         //   repository.addWarehouse(warehouse);
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
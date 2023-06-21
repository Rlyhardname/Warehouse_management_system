package com.example.warehouses.Model;

import com.example.warehouses.STATES.WarehouseCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Warehouse{

    @Id
    private Long id;
    private Long ownerId;
    private Long receiptId;
    private String warehouseName;
    private double squareFeet;
    private double temperature;
    private double humidityPercent; // could combine the two into a class/state or remove one of them;
    private String stockedGoodsType;
    private String warehouseCategory;
    private boolean rented;

}
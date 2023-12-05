package com.example.warehouses.model.warehouse;

import com.example.warehouses.configurations.Enum.WarehouseCategory;
import com.example.warehouses.model.user.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Warehouse implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    // TODO revisit attributes structure, do we need Owner or ownerId is good enough? ect..
    private Long id;
    @ManyToOne()
    private Owner owner;
    @Column(unique=true)
    private String warehouseName;
    @ManyToOne
    private Address address;
    private String squareFeet;
    private String temperature;
    private String humidityPercent;
    private String stockedGoodsType;
    private String warehouseCategory;
    private boolean rented;

    public void init(Owner owner,
                     Address address,
                     String name,
                     String squareFeet,
                     String temperature,
                     String humidityPercent,
                     String stockedGoodsType,
                     WarehouseCategory warehouseCategory
                     ){
        setOwner(owner);
        setAddress(address);
        setWarehouseName(name);
        setSquareFeet(squareFeet);
        setTemperature(temperature);
        setHumidityPercent(humidityPercent);
        setStockedGoodsType(stockedGoodsType);
        setWarehouseCategory(warehouseCategory.name());
        rented = false;
    }

    @Override
    public String toString(){

        return warehouseName + " " + id + " " + squareFeet + " " + temperature + " " +  humidityPercent +
                " " + stockedGoodsType + " " + warehouseCategory + " " + rented;
    }

}
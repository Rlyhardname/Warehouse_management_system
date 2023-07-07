package com.example.warehouses.Model.warehouse;

import com.example.warehouses.Configurations.Enum.WarehouseCategory;
import com.example.warehouses.Model.Notification;
import com.example.warehouses.Model.User.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
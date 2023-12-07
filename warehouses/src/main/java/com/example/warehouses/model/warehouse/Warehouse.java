package com.example.warehouses.model.warehouse;

import com.example.warehouses.configurations.Enum.WarehouseCategory;
import com.example.warehouses.model.user.Owner;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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

    @Id()
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    // TODO revisit attributes structure, do we need Owner or ownerId is good enough? ect..
    private Long id;
    @ManyToOne()
    private Owner owner;
    @Column(unique=true)
    @NotBlank(message = "warehouse must a name!")
    private String name;
    @ManyToOne
    private Address address;
    @NotNull(message = "input square feet in field!")
    private Double squareFeet;
    @Nullable
    private Double temperature;
    @Nullable
    private Double humidityPercent;
    @Nullable
    private String inventory;
    @NotBlank (message = "please choose one of the categories")
    private String category;
    private boolean rented;

    public void init(Owner owner,
                     Address address,
                     String name,
                     Double squareFeet,
                     Double temperature,
                     Double humidityPercent,
                     String stockedGoodsType,
                     WarehouseCategory warehouseCategory
                     ){
        setOwner(owner);
        setAddress(address);
        setName(name);
        setSquareFeet(squareFeet);
        setTemperature(temperature);
        setHumidityPercent(humidityPercent);
        setInventory(stockedGoodsType);
        setCategory(warehouseCategory.name());
        rented = false;
    }

    @Override
    public String toString(){

        return name + " " + id + " " + squareFeet + " " + temperature + " " +  humidityPercent +
                " " + inventory + " " + category + " " + rented;
    }

}
package com.example.warehouses.Model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Warehouse{

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    // @JoinColumn(name = "owner_id", nullable = false)
    // @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    private Owner owner;
    @Column(unique=true)
    private String warehouseName;
//    private Long receiptId;
    private String squareFeet;
    private String temperature;
    private String humidityPercent; // could combine the two into a class/state or remove one of them;
    private String stockedGoodsType;
    private String warehouseCategory;
    private boolean rented;

    public void init(Owner owner,
                     String name,
                     String squareFeet,
                     String temperature,
                     String humidityPercent,
                     String stockedGoodsType,
                     String warehouseCategory,
                     String rented){
        setOwner(owner);
        setWarehouseName(name);
        setSquareFeet(squareFeet);
        setTemperature(temperature);
        setHumidityPercent(humidityPercent);
        setStockedGoodsType(stockedGoodsType);
        setWarehouseCategory(warehouseCategory);
        if(rented.equals("yes")){
            setRented(true);
        }else{
            setRented(false);
        }


    }

    @Override
    public String toString(){

        return warehouseName + " " + id + " " + squareFeet + " " + temperature + " " +  humidityPercent +
                " " + stockedGoodsType + " " + warehouseCategory + " " + rented;
    }

}
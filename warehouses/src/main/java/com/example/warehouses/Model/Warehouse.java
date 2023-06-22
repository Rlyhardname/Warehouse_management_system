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
//    private double squareFeet;
//    private double temperature;
//    private double humidityPercent; // could combine the two into a class/state or remove one of them;
//    private String stockedGoodsType;
//    private String warehouseCategory;
//    private boolean rented;

    public void init(Owner owner, String name){
        setOwner(owner);
        setWarehouseName(name);
    }

    @Override
    public String toString(){

        return warehouseName + " " + id;
    }

}
package com.example.warehouses.model.warehouse;

import com.example.warehouses.model.user.Owner;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
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
    @Column(unique = true)
    @NotBlank(message = "warehouse must a name!")
    private String name;
    @ManyToOne()
    private Address address;
    @NotNull(message = "input square feet in field!")
    private Double squareFeet;
    @Nullable
    private Double temperature;
    @Nullable
    private Double humidityPercent;
    @Nullable
    private String inventory;
    @NotBlank(message = "please choose one of the categories")
    private String category;
    private boolean rented;

    public Warehouse(Owner owner, String name, Address address, Double squareFeet, @Nullable Double temperature, @Nullable Double humidityPercent, @Nullable String inventory, String category) {
        this.owner = owner;
        this.name = name;
        this.address = address;
        this.squareFeet = squareFeet;
        this.temperature = temperature;
        this.humidityPercent = humidityPercent;
        this.inventory = inventory;
        this.category = category;
        this.rented = false;
    }

    public Warehouse(Address address, Owner owner) {
        setOwner(owner);
        setAddress(address);
        rented = false;
    }

    public Warehouse(Address address) {
        setAddress(address);
        rented = false;
    }

    public Warehouse() {
        rented = false;
    }


    @Override
    public String toString() {

        return name + " " + id + " " + squareFeet + " " + temperature + " " + humidityPercent +
                " " + inventory + " " + category + " " + rented;
    }

}
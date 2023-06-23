package com.example.warehouses.Interfaces;

import com.example.warehouses.Model.Warehouse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Table
@Entity
public abstract class Administrator {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long Id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}


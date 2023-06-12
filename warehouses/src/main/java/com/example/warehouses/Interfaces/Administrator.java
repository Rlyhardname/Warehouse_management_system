package com.example.warehouses.Interfaces;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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


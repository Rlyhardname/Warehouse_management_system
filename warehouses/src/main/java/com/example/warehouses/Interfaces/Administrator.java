package com.example.warehouses.Interfaces;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Email
    @Column(unique = true)
    private String email;
    @Size(min = 5)
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

}


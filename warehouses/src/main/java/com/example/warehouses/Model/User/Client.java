package com.example.warehouses.Model.User;

import com.example.warehouses.Model.Notification;
import com.example.warehouses.Model.warehouse.RentalForm;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@DynamicUpdate
public class Client {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long Id;
    @Email(message ="Email must be valid")
    @Column(unique=true)
    private String email;
    @Size(min = 5, message = "Password is too short")
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String accountType;





}
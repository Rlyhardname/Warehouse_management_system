package com.example.warehouses.Model.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table

@Entity
@DynamicUpdate
public class Client {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
            //strategy = GenerationType.AUTO
    )
    // UUID
    private Long Id;
    @Email(message = "Email doesn't exists or is not in valid format")
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    @Size(min = 4, max = 20, message = "Passwords needs to be between 4 and 20 characters")
    private String password;
    @NotBlank
    @NotNull
    @Size(min = 2, max = 30, message = "Name is too short or too long")
    private String firstName;
    @NotBlank
    @NotNull
    @Size(min = 2, max = 30, message = "Name is too short or too long")
    private String lastName;
    @NotNull
    private String accountType;

}

package com.example.warehouses.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(
        name = "client",
        uniqueConstraints = {
                @UniqueConstraint(name = "email_constraints", columnNames = "email")
        }
)

@DynamicUpdate
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
            //strategy = GenerationType.AUTO
    )
    // UUID
    private Long Id;
    @Email(message = "Email doesn't exists or is not in valid format")
    @Column(
            name = "email",
            nullable = false,
            unique = true
    )

    private String email;
    @Size(min = 4, max = 20, message = "Passwords needs to be between 4 and 20 characters")
    private String password;
    @NotBlank
    @Size(min = 2, max = 30, message = "Name is too short or too long")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 30, message = "Name is too short or too long")
    private String lastName;
    @Column(name = "dtype", insertable = false, updatable = false)
    private String dType;
}

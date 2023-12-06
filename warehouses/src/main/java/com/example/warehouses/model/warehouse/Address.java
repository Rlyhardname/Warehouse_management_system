package com.example.warehouses.model.warehouse;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Address {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @NotBlank
    private String county;
    @NotBlank
    private String town;
    @NotBlank
    private String streetName;

    public void init(  String county,
     String town,
     String streetName
    ){
        setCounty(county);
        setTown(town);
        setStreetName(streetName);
    }
}

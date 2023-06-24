package com.example.warehouses.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

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
    private String county;
    private String town;
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

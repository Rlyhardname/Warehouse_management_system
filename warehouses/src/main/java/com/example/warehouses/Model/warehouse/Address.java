package com.example.warehouses.Model.warehouse;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

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
    @OneToMany(orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "address_id")
    private List<Warehouse> warehouseList;



    public void init(  String county,
     String town,
     String streetName
    ){
        setCounty(county);
        setTown(town);
        setStreetName(streetName);
    }
}

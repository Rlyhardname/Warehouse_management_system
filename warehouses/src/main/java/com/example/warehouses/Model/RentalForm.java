package com.example.warehouses.Model;

import com.example.warehouses.Interfaces.Client;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class RentalForm {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long Id;
    @ManyToOne
    private Agent agent;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Warehouse warehouse;
    private LocalDate startDate;
    private LocalDate endDate;
    private double rentPerMonth;

    public RentalForm(Agent agent, Client client, Warehouse warehouse, LocalDate startDate, LocalDate endDate, double rentPerMonth) {
        this.agent = agent;
        this.client = client;
        this.warehouse = warehouse;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentPerMonth = rentPerMonth;
        warehouse.setRented(true);
    }
}
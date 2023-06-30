package com.example.warehouses.Model.warehouse;

import com.example.warehouses.Model.User.Agent;
import com.example.warehouses.Model.User.Client;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    private double contractFiatWorth;
    private double agentFee;
    private Long contractInDays;

    public RentalForm(Agent agent, Client client, Warehouse warehouse, LocalDate startDate, LocalDate endDate, double contractFiatWorth, double agentFee) {
        this.agent = agent;
        this.client = client;
        this.warehouse = warehouse;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractFiatWorth = contractFiatWorth;
        this.agentFee = agentFee;
        warehouse.setRented(true);
        calcContractInMonths();
        System.out.println(contractInDays);
    }

    public void calcContractInMonths(){
        contractInDays = ChronoUnit.DAYS.between(startDate,endDate);
    }
}
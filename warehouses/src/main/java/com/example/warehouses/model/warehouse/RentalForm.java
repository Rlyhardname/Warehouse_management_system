package com.example.warehouses.model.warehouse;

import com.example.warehouses.model.user.Agent;
import com.example.warehouses.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private User customer;
    @ManyToOne
    private Warehouse warehouse;
    @Future(message = "Date must be in the future")
    @NotNull(message = "Date cannot be left blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Future(message = "Date must be in the future")
    @NotNull(message = "Date cannot be left blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotNull(message = "Fill out fee contract field")
    @DecimalMin(value = "997.0")
    private double contractFiatWorth;
    @NotNull(message = "Fill out fee field")
    @DecimalMin(value = "0.1")
    private double agentFee;
    private Long contractInDays;

    public RentalForm(User agent, User customer, Warehouse warehouse, LocalDate startDate, LocalDate endDate, double contractFiatWorth, double agentFee) {
        this.agent = (Agent) agent;
        this.customer = customer;
        this.warehouse = warehouse;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractFiatWorth = contractFiatWorth;
        this.agentFee = agentFee;
        warehouse.setRented(true);
        setContractDays();
    }

    public void setContractDays() {
        contractInDays = ChronoUnit.DAYS.between(startDate, endDate);
    }
}
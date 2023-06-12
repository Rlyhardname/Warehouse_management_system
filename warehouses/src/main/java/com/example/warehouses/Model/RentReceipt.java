package com.example.warehouses.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class RentReceipt{
    @Id
    private Long Id;
    private Long clientId;
    private Timestamp startDate;
    private Timestamp endDate;
    private double rentPerMonth;
}
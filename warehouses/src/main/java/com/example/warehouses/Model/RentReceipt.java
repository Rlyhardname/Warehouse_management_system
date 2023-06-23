package com.example.warehouses.Model;

import jakarta.persistence.*;
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
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long Id;
    private Long clientId;
    private Timestamp startDate;
    private Timestamp endDate;
    private double rentPerMonth;
}
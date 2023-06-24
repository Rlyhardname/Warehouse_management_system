package com.example.warehouses.DTO;

import com.example.warehouses.Model.Warehouse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class RentFormDTO {

    private Long Id;
    private Long agentId;
    private Long clientId;
    private Long warehouseId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double rentPerMonth;

    public RentFormDTO(Long id, Long agentId, Long clientId, Long warehouseId, LocalDate startDate, LocalDate endDate, double rentPerMonth) {
        Id = id;
        this.agentId = agentId;
        this.clientId = clientId;
        this.warehouseId = warehouseId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentPerMonth = rentPerMonth;
    }
}

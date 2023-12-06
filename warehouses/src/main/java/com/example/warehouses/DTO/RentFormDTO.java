package com.example.warehouses.DTO;

import java.time.LocalDate;

// TODO think about if we need the record id or not...
public record RentFormDTO(Long id,
                          Long agentId,
                          Long customerId,
                          Long warehouseId,
                          LocalDate startDate,
                          LocalDate endDate,
                          double contractFiatWorth) {
}

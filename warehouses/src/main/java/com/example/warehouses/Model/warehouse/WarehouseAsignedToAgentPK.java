package com.example.warehouses.Model.warehouse;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class WarehouseAsignedToAgentPK implements Serializable {

    private Long agentId;
    private Long warehouseId;

    public WarehouseAsignedToAgentPK(Long agentId, Long warehouseId) {
        this.agentId = agentId;
        this.warehouseId = warehouseId;
    }
}
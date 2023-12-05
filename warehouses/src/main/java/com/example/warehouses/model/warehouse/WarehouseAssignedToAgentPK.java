package com.example.warehouses.model.warehouse;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class WarehouseAssignedToAgentPK implements Serializable {

    private Long agentId;
    private Long warehouseId;

    public WarehouseAssignedToAgentPK(Long agentId, Long warehouseId) {
        this.agentId = agentId;
        this.warehouseId = warehouseId;
    }
}

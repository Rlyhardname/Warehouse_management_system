package com.example.warehouses.model.warehouse;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Entity
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class WarehouseAssignedToAgent {
    @EmbeddedId
    private WarehouseAsignedToAgentPK id;
    private String relationshipStatus;

    public WarehouseAssignedToAgent(WarehouseAsignedToAgentPK id) {
        this.id = id;
        relationshipStatus = "NOT_CONTRACTED";
    }
}

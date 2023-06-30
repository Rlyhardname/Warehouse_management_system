package com.example.warehouses.Model.warehouse;

import com.example.warehouses.Model.User.Owner;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@Entity
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class WarehouseAssignedToAgent {
    @EmbeddedId
    private WarehouseAsignedToAgentPK id;
    @ManyToOne
    private Owner owner;
    private String relationshipStatus;

    public WarehouseAssignedToAgent(WarehouseAsignedToAgentPK id) {
        this.id = id;
        relationshipStatus = "NOT_CONTRACTED";
    }
}

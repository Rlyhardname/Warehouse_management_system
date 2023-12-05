package com.example.warehouses.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Embeddable
public class AgentRatingsPK implements Serializable {

    @Min(value = 1)
    private Long ownerID;
    @Min(value = 1)
    private Long agentID;

    public AgentRatingsPK(Long ownerID, Long agentID) {
        this.ownerID = ownerID;
        this.agentID = agentID;
    }

    public AgentRatingsPK(){

    }
}

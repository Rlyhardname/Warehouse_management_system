package com.example.warehouses.Model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Embeddable
public class AgentRatingsPK implements Serializable {

    private Long ownerID;
    private Long agentID;

    public AgentRatingsPK(Long ownerID, Long agentID) {
        this.ownerID = ownerID;
        this.agentID = agentID;
    }

    public AgentRatingsPK(){

    }
}

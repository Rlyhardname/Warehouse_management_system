package com.example.warehouses.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class AgentRatings {
    @EmbeddedId
    private AgentRatingsPK id;
    private int stars;
}

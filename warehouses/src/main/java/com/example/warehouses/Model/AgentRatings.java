package com.example.warehouses.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AgentRatings {
    @EmbeddedId
    private AgentRatingsPK id;
    private int stars;
}

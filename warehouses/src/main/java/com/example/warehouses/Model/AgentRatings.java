package com.example.warehouses.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AgentRatings {
    @EmbeddedId
    @Unique
    private AgentRatingsPK id;
    @Min(value = 1, message = "Min is 1")
    @Max(value = 5, message = "Max is 5")
    private int stars;
}

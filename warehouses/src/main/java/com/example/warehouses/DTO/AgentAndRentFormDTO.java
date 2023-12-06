package com.example.warehouses.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


public record AgentAndRentFormDTO(
        String firstName,
        String lastName,
        String email,
        double starsTotal,
        int numberOfVotes,
        List<RentFormDTO> rentalForms) {


}

package com.example.warehouses.DTO;

import java.util.List;

public record AgentAndRentFormDTO(
        String firstName,
        String lastName,
        String email,
        double starsTotal,
        int numberOfVotes,
        List<RentFormDTO> rentalForms) {


}

package com.example.warehouses.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class AgentAndRentFormDTO {

    private String firstName;
    private String lastName;
    private String email;
    private double starsTotal;
    private int numberOfVotes;
    private List<RentFormDTO> rentalForms;

    public AgentAndRentFormDTO(){
        starsTotal = 0;
        numberOfVotes = -1;
        rentalForms = new ArrayList<>();
    }

}

package com.example.warehouses.DTO;

import com.example.warehouses.Model.User.Agent;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AgentDTO {

    private Long id;
    private String firstName;
    private String lastName;

    public AgentDTO(Agent agent){
       id =  agent.getId();
       firstName = agent.getFirstName();
       lastName = agent.getLastName();
    }
}

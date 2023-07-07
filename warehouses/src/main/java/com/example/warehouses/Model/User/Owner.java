package com.example.warehouses.Model.User;

import com.example.warehouses.Configurations.Enum.WarehouseCategory;
import com.example.warehouses.Model.AgentRatings;
import com.example.warehouses.Model.AgentRatingsPK;
import com.example.warehouses.Model.warehouse.Address;
import com.example.warehouses.Model.warehouse.Warehouse;
import com.example.warehouses.Model.warehouse.WarehouseAsignedToAgentPK;
import com.example.warehouses.Model.warehouse.WarehouseAssignedToAgent;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@RequiredArgsConstructor
public class Owner extends Client {

    public void init(String email, String password, String firstName, String lastName) {
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setAccountType("owner");
    }

    public Warehouse CreatedWarehouse(Address address,
                                                String name,
                                                String squareFeet,
                                                String temperature,
                                                String humidityPercent,
                                                String stockedGoodsType,
                                                WarehouseCategory warehouseCategory
    ) {
        Warehouse warehouse = new Warehouse();
        warehouse.init(this,
                address,
                name,
                squareFeet,
                temperature,
                humidityPercent,
                stockedGoodsType,
                warehouseCategory);
        return warehouse;
    }

    public List<WarehouseAssignedToAgent> assignAgentsToWarehouse(List<Agent> agents, Warehouse warehouse) {

        List<WarehouseAssignedToAgent> listOfAgentsAssignedToWarehouse = new ArrayList<>();
        for (Agent agentToAssign : agents
        ) {
            WarehouseAsignedToAgentPK PK = new WarehouseAsignedToAgentPK(agentToAssign.getId(), warehouse.getId());
            WarehouseAssignedToAgent agentAssigned = new WarehouseAssignedToAgent(PK);
            listOfAgentsAssignedToWarehouse.add(agentAssigned);
        }

        return listOfAgentsAssignedToWarehouse;
    }

    public List<WarehouseAssignedToAgent> RemoveAgentsFromWarehouse(List<Agent> agents,
                                                                    Warehouse warehouse,
                                                                    List<WarehouseAssignedToAgent> assignedAgents) {
        List<WarehouseAssignedToAgent> cleanAgentList = new ArrayList<>(assignedAgents);
        for (Agent agent : agents
        ) {
            for (WarehouseAssignedToAgent assignedAgent : assignedAgents
            ) {
                if (agent.getId() == assignedAgent.getId().getAgentId() &&
                        warehouse.getId() == assignedAgent.getId().getWarehouseId()
                && assignedAgent.getRelationshipStatus().equals("CONTRACTED")) {
                    WarehouseAsignedToAgentPK PK = new WarehouseAsignedToAgentPK(agent.getId(), warehouse.getId());
                    WarehouseAssignedToAgent agentWarehouse = new WarehouseAssignedToAgent(PK);
                    cleanAgentList.remove(agentWarehouse);
                }
            }
        }

        return cleanAgentList;
    }

    public AgentRatings rateAgent(Agent agent, int stars) {

        AgentRatingsPK PK = new AgentRatingsPK(this.getId(), agent.getId());
        AgentRatings rating = new AgentRatings(PK, stars);
        return rating;
    }
}
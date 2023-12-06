package com.example.warehouses.util;

import com.example.warehouses.configurations.Enum.WarehouseCategory;
import com.example.warehouses.model.AgentRatings;
import com.example.warehouses.model.AgentRatingsPK;
import com.example.warehouses.model.user.Agent;
import com.example.warehouses.model.user.Owner;
import com.example.warehouses.model.user.User;
import com.example.warehouses.model.warehouse.Address;
import com.example.warehouses.model.warehouse.Warehouse;
import com.example.warehouses.model.warehouse.WarehouseAssignedToAgentPK;
import com.example.warehouses.model.warehouse.WarehouseAssignedToAgent;
import com.example.warehouses.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;

public class OwnerUtil {

    public static Warehouse CreatedWarehouse(Owner owner,
                                             Address address,
                                             String name,
                                             String squareFeet,
                                             String temperature,
                                             String humidityPercent,
                                             String stockedGoodsType,
                                             WarehouseCategory warehouseCategory
    ) {
        Warehouse warehouse = new Warehouse();
        warehouse.init(owner,
                address,
                name,
                squareFeet,
                temperature,
                humidityPercent,
                stockedGoodsType,
                warehouseCategory);
        return warehouse;
    }

    public static List<WarehouseAssignedToAgent> assignAgentsToWarehouse(List<Agent> agents, Warehouse warehouse) {

        List<WarehouseAssignedToAgent> listOfAgentsAssignedToWarehouse = new ArrayList<>();
        for (Agent agentToAssign : agents
        ) {
            WarehouseAssignedToAgentPK PK = new WarehouseAssignedToAgentPK(agentToAssign.getId(), warehouse.getId());
            WarehouseAssignedToAgent agentAssigned = new WarehouseAssignedToAgent(PK);
            listOfAgentsAssignedToWarehouse.add(agentAssigned);
        }

        return listOfAgentsAssignedToWarehouse;
    }

    public static List<WarehouseAssignedToAgent> RemoveAgentsFromWarehouse(List<Agent> agents,
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
                    WarehouseAssignedToAgentPK PK = new WarehouseAssignedToAgentPK(agent.getId(), warehouse.getId());
                    WarehouseAssignedToAgent agentWarehouse = new WarehouseAssignedToAgent(PK);
                    cleanAgentList.remove(agentWarehouse);
                }
            }
        }

        return cleanAgentList;
    }

    public static AgentRatings rateAgent(Long ownerId, Agent agent, int stars) {
        AgentRatingsPK PK = new AgentRatingsPK(ownerId, agent.getId());
        AgentRatings rating = new AgentRatings(PK, stars);
        return rating;
    }

    public static List<Agent> getAllAgentsById(UsersRepository usersRepository, List<Long> agentIds) {
        List<Agent> agents = new ArrayList<>();
        for (Long agentId : agentIds
        ) {
            User agent = usersRepository.findById(agentId).get();
            if (agent != null) {
                if (agent.getDType().equals("agent")) {
                    agents.add((Agent) agent);
                }
            }
        }

        return agents;
    }

    public static List<Long> getAllAgentIds(List<WarehouseAssignedToAgent> agentWarehousePair) {
        List<Long> agentIds = new ArrayList<>();
        for (WarehouseAssignedToAgent pair : agentWarehousePair
        ) {
            agentIds.add(pair.getId().getAgentId());
        }
        return agentIds;
    }
}

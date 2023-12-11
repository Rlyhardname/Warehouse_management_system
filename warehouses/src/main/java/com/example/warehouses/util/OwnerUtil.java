package com.example.warehouses.util;

import com.example.warehouses.configurations.Enum.WarehouseCategory;
import com.example.warehouses.model.AgentRatings;
import com.example.warehouses.model.AgentRatingsPK;
import com.example.warehouses.model.user.Agent;
import com.example.warehouses.model.user.Owner;
import com.example.warehouses.model.user.UserImpl;
import com.example.warehouses.model.warehouse.Address;
import com.example.warehouses.model.warehouse.Warehouse;
import com.example.warehouses.model.warehouse.WarehouseAssignedToAgentPK;
import com.example.warehouses.model.warehouse.WarehouseAssignedToAgent;
import com.example.warehouses.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class OwnerUtil {
    /**
     * Creates a warehouse object instance <pre>
     * @Deprecated Not recommended to use.
     * This method isn't flexible enough. Replaced by
     * {@link com.example.warehouses.model.warehouse.WarehouseBuilder} interface
     * {@link com.example.warehouses.model.warehouse.WarehouseBuilderImpl} implementation</pre>
     */
    @Deprecated
    public static Warehouse CreatedWarehouse(Owner owner,
                                             Address address,
                                             String name,
                                             Double squareFeet,
                                             Double temperature,
                                             Double humidityPercent,
                                             String stockedGoodsType,
                                             WarehouseCategory warehouseCategory
    ) {
        Warehouse warehouse = new Warehouse(owner,
                name,
                address,
                squareFeet,
                temperature,
                humidityPercent,
                stockedGoodsType,
                warehouseCategory.name());
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
                if (Objects.equals(agent.getId(), assignedAgent.getId().getAgentId())
                        && Objects.equals(warehouse.getId(), assignedAgent.getId().getWarehouseId())
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
        for (Long agentId : agentIds) {
            UserImpl agent = usersRepository.findById(agentId).get();
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

    public static WarehouseCategory warehouseCategory(String category) {
        switch (category.toUpperCase()) {
            case "GARAGE":
                return WarehouseCategory.GARAGE;
            case "SMALL":
                return WarehouseCategory.SMALL;
            case "MEDIUM":
                return WarehouseCategory.MEDIUM;
            case "LARGE":
                return WarehouseCategory.LARGE;
            case "INDUSTRIAL":
                return WarehouseCategory.INDUSTRIAL;
            default:
                return WarehouseCategory.EMPTY;
        }
    }
}

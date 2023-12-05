package com.example.warehouses.services;

import com.example.warehouses.configurations.Enum.Role;
import com.example.warehouses.configurations.Enum.WarehouseCategory;
import com.example.warehouses.DTO.AgentDTO;
import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.exception.Client.*;
import com.example.warehouses.exception.Warehouse.WarehouseAlreadyExistsException;
import com.example.warehouses.exception.Warehouse.WarehouseNotExistingException;
import com.example.warehouses.model.AgentRatings;
import com.example.warehouses.model.user.Agent;
import com.example.warehouses.model.user.User;
import com.example.warehouses.model.user.Owner;
import com.example.warehouses.model.warehouse.Address;
import com.example.warehouses.model.warehouse.Warehouse;
import com.example.warehouses.model.warehouse.WarehouseAssignedToAgent;
import com.example.warehouses.repository.*;
import com.example.warehouses.util.OwnerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OwnerService {

    private final UsersRepository usersRepository;
    private final UsersService globalService;
    private final WarehouseRepository warehouseRepository;
    private final RatingsRepository ratingsRepository;
    private final RentalFormRepository rentalFormRepository;
    private final WarehouseAssignedToAgentRepository marketRepository;

    private final AddressRepository addressRepository;
    private final WarehouseAssignedToAgentRepository warehouseAssignedToAgentRepository;

    @Autowired
    public OwnerService(UsersRepository usersRepository,
                        UsersService globalService,
                        WarehouseRepository warehouseRepository,
                        RatingsRepository ratingsRepository,
                        RentalFormRepository rentalFormRepository,
                        WarehouseAssignedToAgentRepository marketRepository,
                        AddressRepository addressRepository,
                        WarehouseAssignedToAgentRepository warehouseAssignedToAgentRepository) {
        this.usersRepository = usersRepository;
        this.globalService = globalService;
        this.warehouseRepository = warehouseRepository;
        this.ratingsRepository = ratingsRepository;
        this.rentalFormRepository = rentalFormRepository;
        this.marketRepository = marketRepository;
        this.addressRepository = addressRepository;
        this.warehouseAssignedToAgentRepository = warehouseAssignedToAgentRepository;
    }

    public WarehouseDTO createWarehouse(String email,
                                        String county,
                                        String town,
                                        String streetName,
                                        String name,
                                        String squareFeet,
                                        String temperature,
                                        String humidityPercent,
                                        String stockedGoodsType,
                                        WarehouseCategory warehouseCategory,
                                        String rented) {
        Warehouse warehouse = null;
        WarehouseDTO warehouseDTO = null;

        User ownerOpt = usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotExististingException()
        );
        if (warehouseRepository.findWarehouseByName(name).isPresent() == false) {
            // TODO possibly not need owner object here, make repo method for boolean check
            Owner owner = (Owner) ownerOpt;
            Address address = new Address();
            address.init(county, town, streetName);
            addressRepository.save(address);

            warehouse = OwnerUtil.CreatedWarehouse(owner,
                    address,
                    name,
                    squareFeet,
                    temperature,
                    humidityPercent,
                    stockedGoodsType,
                    warehouseCategory);
            warehouseRepository.save(warehouse);
            warehouseDTO = new WarehouseDTO(warehouse);
        } else {
            throw new WarehouseAlreadyExistsException();
        }
        WarehouseDTO warehouseDTOOpt = warehouseDTO;
        return warehouseDTOOpt;
    }


    public List<AgentRatings> rateAgent(Long ownerId, Long agentId, int stars) {
        // TODO possibly not need owner object here, make repo method for boolean check
        Owner owner = (Owner) usersRepository.findById(ownerId).orElseThrow(
                () -> new UserNotExististingException()
        );
        Agent agent = (Agent) usersRepository.findById(agentId).orElseThrow(
                () -> new UserNotExististingException()
        );

        AgentRatings rating = OwnerUtil.rateAgent(owner.getId(),agent, stars);
        ratingsRepository.save(rating);

        List<AgentRatings> allRatings = ratingsRepository.findAllByAgentId(agentId).get();

        return allRatings;
    }

    public Optional<List<Warehouse>> getWarehouseByOwnerId(Long ownerId) {

        Optional<List<Warehouse>> warehouseOpt = warehouseRepository.findWarehousesByOwnerId(ownerId);
        return warehouseOpt;
    }

    public Optional<List<Warehouse>> getAllWarehouses(String rented) {

        switch (rented) {
            case "yes":
                return warehouseRepository.findByRentStatus(true);
            case "no":
                return warehouseRepository.findByRentStatus(false);
            default:
                return Optional.of(warehouseRepository.findAll());
        }

    }

    public Set<Agent> setAgentsToWarehouse(Long ownerId, List<Long> agentIds, Long warehouseId) { // AgentsWarehouse DTO // List<Warehouse> warehouses, List<Agent> agents

        List<Agent> agents = getAllAgentsById(agentIds);
        Warehouse warehouse = warehouseRepository.findWarehouseByOwnerIdAndWarehouseId(ownerId, warehouseId).get();
        // TODO possibly not need owner object here, make repo method for boolean check
        Owner owner = (Owner) usersRepository.findById(ownerId).get();
        warehouseAssignedToAgentRepository.saveAll(OwnerUtil.assignAgentsToWarehouse(agents, warehouse));

        return getAllAgentsPairedToWarehouse(ownerId, warehouseId);
    }

    public Set<AgentDTO> RemoveAgentsFromWarehouse(Long ownerId,
                                                   List<Long> agents,
                                                   Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(
                () -> new WarehouseNotExistingException()
        );
        // TODO possibly not need owner object here, make repo method for boolean check
        Owner owner = (Owner) usersRepository.findById(ownerId).orElseThrow(
                () -> new UserNotExististingException()
        );
        List<Agent> agentsToRemove = getAllAgentsById(agents);
        List<WarehouseAssignedToAgent> assignedAgents = getAllAssignedAgentsToWarehouse(agentsToRemove, warehouse);
        List<WarehouseAssignedToAgent> agentDataList = OwnerUtil.RemoveAgentsFromWarehouse(agentsToRemove, warehouse, assignedAgents);

        warehouseAssignedToAgentRepository.deleteAll(agentDataList);

        agentDataList = new ArrayList<>();
        agentDataList = warehouseAssignedToAgentRepository.findByWarehouseId(warehouseId).get();

        Set<AgentDTO> agentDTOset = new HashSet<>();
        List<Agent> agentList = getAllAgentsById(getAllAgentIds(agentDataList));
        for (Agent agent : agentList
        ) {
            agentDTOset.add(new AgentDTO(agent));
        }

        return agentDTOset;
    }

    public List<Long> getAllAgentIds(List<WarehouseAssignedToAgent> agentWarehousePair) {
        List<Long> agentIds = new ArrayList<>();
        for (WarehouseAssignedToAgent pair : agentWarehousePair
        ) {
            agentIds.add(pair.getId().getAgentId());
        }
        return agentIds;
    }

    public List<WarehouseAssignedToAgent> getAllAssignedAgentsToWarehouse(List<Agent> agentList, Warehouse warehouse) {

        List<WarehouseAssignedToAgent> assignedAgents = new ArrayList<>();
        for (Agent agent : agentList
        ) {
            {
                Optional<WarehouseAssignedToAgent> agentWarehousePair =
                        warehouseAssignedToAgentRepository.findByAgentIdAndWarehouseId(agent.getId(), warehouse.getId());
                if (agentWarehousePair.isPresent()) {
                    assignedAgents.add(agentWarehousePair.get());
                }

            }

        }
        return assignedAgents;
    }

    public WarehouseCategory warehouseCategory(String category) {
        switch (category.toLowerCase()) {
            case "garage":
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

    public Set<Agent> getAllAgentsPairedToWarehouse(Long ownerId, Long warehouseId) {
        Set<Agent> agentSet = new HashSet<>();
        Owner owner = (Owner) usersRepository.findById(ownerId).get(); //
        Warehouse warehouse = warehouseRepository.findById(warehouseId).get(); //
        List<WarehouseAssignedToAgent> warehouseAgentPair = new ArrayList<>();
        if (warehouse.getOwner().getId() == owner.getId()) {
            warehouseAgentPair = warehouseAssignedToAgentRepository.findByWarehouseId(warehouseId).get();
        }

        List<Agent> agents = new ArrayList<>();
        for (WarehouseAssignedToAgent pair : warehouseAgentPair
        ) {
            Agent agent = (Agent) usersRepository.findById(pair.getId().getAgentId()).get();
            agents.add(agent);
        }

        for (Agent agent : agents
        ) {
            agentSet.remove(agent);
        }

        return agentSet;
    }

    public List<Agent> getAllAgentsById(List<Long> agentIds) {
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

    public List<Warehouse> fetchWarehouses(Long ownerId) {
        return warehouseRepository.findWarehousesByOwnerId(ownerId).orElseThrow(
                () -> new OwnerDoesntOwnAnyWarehouseException()
        );
    }

    public Set<AgentDTO> getAllAgents() {
        List<User> agents = usersRepository.findBydType(Role.AGENT.name());
        Set<AgentDTO> agentsDTO = new HashSet<>();
        if (!agents.isEmpty()) {
            for (User agent : agents
            ) {
                agentsDTO.add(new AgentDTO((Agent) agent));
            }
        }
        return agentsDTO;
    }
}


package com.example.warehouses.Services;

import com.example.warehouses.Configurations.Enum.WarehouseCategory;
import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.DTO.AgentDTO;
import com.example.warehouses.DTO.RentFormDTO;
import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.Exception.Client.AgentHasNoContractsException;
import com.example.warehouses.Exception.Client.OwnerDoesntOwnAnyWarehouseException;
import com.example.warehouses.Exception.Client.UserNotExististingException;
import com.example.warehouses.Exception.Warehouse.WarehouseAlreadyExistsException;
import com.example.warehouses.Exception.Warehouse.WarehouseNotExistingException;
import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Model.*;
import com.example.warehouses.Model.User.Agent;
import com.example.warehouses.Model.User.Owner;
import com.example.warehouses.Model.warehouse.*;
import com.example.warehouses.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ClientFuncService {

    private final ClientRepository clientRepository;
    private final GlobalService globalService;
    private final WarehouseRepository warehouseRepository;
    private final RatingsRepository ratingsRepository;
    private final RentalFormRepository rentalFormRepository;
    private final WarehouseAssignedToAgentRepository marketRepository;

    private final AddressRepository addressRepository;
    private final WarehouseAssignedToAgentRepository warehouseAssignedToAgentRepository;

    @Autowired
    public ClientFuncService(ClientRepository clientRepository,
                             GlobalService globalService,
                             WarehouseRepository warehouseRepository,
                             RatingsRepository ratingsRepository,
                             RentalFormRepository rentalFormRepository,
                             WarehouseAssignedToAgentRepository marketRepository,
                             AddressRepository addressRepository,
                             WarehouseAssignedToAgentRepository warehouseAssignedToAgentRepository) {
        this.clientRepository = clientRepository;
        this.globalService = globalService;
        this.warehouseRepository = warehouseRepository;
        this.ratingsRepository = ratingsRepository;
        this.rentalFormRepository = rentalFormRepository;
        this.marketRepository = marketRepository;
        this.addressRepository = addressRepository;
        this.warehouseAssignedToAgentRepository = warehouseAssignedToAgentRepository;
    }

    public Optional<WarehouseDTO> createWarehouse(String email,
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
        System.out.println(email + " " + name);
        Warehouse warehouse = null;
        WarehouseDTO warehouseDTO = null;
        Optional<Warehouse> warehouseOpt = warehouseRepository.findWarehouseByName(name);
        Optional<Client> ownerOpt = Optional.of(clientRepository.findClientByEmail(email).orElseThrow(
                () -> new UserNotExististingException()
        ));
        if (warehouseOpt.isPresent() == false) {
            Owner owner = (Owner) ownerOpt.get();
            Address address = new Address();
            address.init(county, town, streetName);
            addressRepository.save(address);

            warehouse = new Warehouse();
            warehouse.init(owner,
                    address,
                    name,
                    squareFeet,
                    temperature,
                    humidityPercent,
                    stockedGoodsType,
                    warehouseCategory
            );
            warehouseRepository.save(warehouse);
            warehouse = warehouseRepository.findWarehouseByName(name).get();
            //  owner.CreatedWarehouse(warehouse);
            clientRepository.save(owner);
            warehouseDTO = new WarehouseDTO(warehouse);
        } else {
            throw new WarehouseAlreadyExistsException();
        }
        System.out.println(warehouse);

        Optional<WarehouseDTO> warehouseDTOOpt = Optional.of(warehouseDTO);
        return warehouseDTOOpt;
    }

    public AgentAndRentFormDTO getAgentContractsAndRatingsByPeriod(Long agentId, LocalDate startDate, LocalDate endDate) {

        AgentAndRentFormDTO agentDTO = new AgentAndRentFormDTO();
        isAgentRated(agentDTO, agentId);
        gatherFormData(agentDTO, agentId, startDate, endDate);
        for (RentFormDTO form : agentDTO.getRentalForms()
        ) {
            System.out.println("Form" + form);
        }
        return agentDTO;
    }

    public boolean isAgentRated(AgentAndRentFormDTO agentDTO, Long agentId) {
        Agent agent = (Agent) clientRepository.findById(agentId).orElseThrow(
                () -> new UserNotExististingException()
        );
        agentDTO.setEmail(agent.getEmail());
        agentDTO.setFirstName(agent.getFirstName());
        agentDTO.setLastName(agent.getLastName());

        double ratingsTotal = 0;
        int numberOfVotes = 0;
        Optional<List<AgentRatings>> ratingsOptList = ratingsRepository.findByAgentId(agentId);
        if (ratingsOptList.isPresent() == true) {
            numberOfVotes = ratingsOptList.get().size();
            for (AgentRatings rating : ratingsOptList.get()
            ) {
                ratingsTotal += rating.getStars();
            }
        } else {
            return false;
        }
        agentDTO.setStarsTotal(ratingsTotal);
        agentDTO.setNumberOfVotes(numberOfVotes);

        return true;
    }

    public void gatherFormData(AgentAndRentFormDTO agentDTO, Long agentId, LocalDate startDate, LocalDate endDate) {

        List<RentalForm> rentalForms =
                rentalFormRepository.findRentFormsByAgentIdAndStartDateEndDate(agentId, startDate, endDate).orElseThrow(
                        ()-> new AgentHasNoContractsException()
                );


        for (RentalForm form : rentalForms
        ) {

            agentDTO.getRentalForms().add(new RentFormDTO(form.getId(), form.getAgent().getId(), form.getClient().getId(),
                    form.getWarehouse().getId(), form.getStartDate(), form.getEndDate(), form.getContractFiatWorth()));
            System.out.println(form);
        }


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
        Owner owner = (Owner) clientRepository.findById(ownerId).get();
        warehouseAssignedToAgentRepository.saveAll(owner.assignAgentsToWarehouse(agents, warehouse));

        return getAllAgentsPairedToWarehouse(ownerId, warehouseId);
    }

    public Set<AgentDTO> RemoveAgentsFromWarehouse(Long ownerId,
                                                                    List<Long> agents,
                                                                    Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(
                () -> new WarehouseNotExistingException()
        );
        Owner owner = (Owner) clientRepository.findById(ownerId).orElseThrow(
                () -> new UserNotExististingException()
        );
        List<Agent> agentsToRemove = getAllAgentsById(agents);
        List<WarehouseAssignedToAgent> assignedAgents = getAllAssignedAgentsToWarehouse(agentsToRemove, warehouse);
        List<WarehouseAssignedToAgent> agentsLeft = owner.RemoveAgentsFromWarehouse(agentsToRemove, warehouse, assignedAgents);

        Set<AgentDTO> agentDTOset = new HashSet<>();
        List<Agent> agentList =getAllAgentsById(getAllAgentIds(agentsLeft));
        for (Agent agent: agentList
             ) {
            agentDTOset.add(new AgentDTO(agent));
        }

        return agentDTOset;
    }

    public List<Long> getAllAgentIds(List<WarehouseAssignedToAgent> agentWarehousePair){
       List<Long> agentIds = new ArrayList<>();
        for (WarehouseAssignedToAgent pair: agentWarehousePair
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
                if (agentWarehousePair != null) {
                    assignedAgents.add(agentWarehousePair.get());
                }

            }

        }
        return assignedAgents;
    }

    public Set<Agent> getAllAgentsPairedToWarehouse(Long ownerId, Long warehouseId) {
        Set<Agent> agentSet = new HashSet<>();
        List<WarehouseAssignedToAgent> warehouseAgentPair = warehouseAssignedToAgentRepository.findWarehousesByOwnerIdAndWarehouseId(ownerId, warehouseId).get();
        List<Agent> agents = new ArrayList<>();
        for (WarehouseAssignedToAgent pair : warehouseAgentPair
        ) {
            Agent agent = (Agent) clientRepository.findById(pair.getId().getAgentId()).get();
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
            Agent agent = (Agent) clientRepository.findById(agentId).get();
            if (agent != null) {
                agents.add(agent);
            }
        }

        return agents;
    }

    public List<Warehouse> fetchWarehouses(Long ownerId) {
        return warehouseRepository.findWarehousesByOwnerId(ownerId).orElseThrow(
                () -> new OwnerDoesntOwnAnyWarehouseException()
        );
    }
}


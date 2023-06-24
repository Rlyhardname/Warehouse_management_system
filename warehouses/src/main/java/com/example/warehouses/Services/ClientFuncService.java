package com.example.warehouses.Services;

import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.DTO.RentFormDTO;
import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.Exception.Client.UserNotExististingException;
import com.example.warehouses.Exception.Warehouse.WarehouseAlreadyExistsException;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.*;
import com.example.warehouses.Repository.ClientRepository;
import com.example.warehouses.Repository.RatingsRepository;
import com.example.warehouses.Repository.RentalFormRepository;
import com.example.warehouses.Repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClientFuncService {

    private final ClientRepository clientRepository;
    private final GlobalService globalService;
    private final WarehouseRepository warehouseRepository;
    private final RatingsRepository ratingsRepository;
    private final RentalFormRepository rentalFormRepository;

    @Autowired
    public ClientFuncService(ClientRepository clientRepository,
                             GlobalService globalService,
                             WarehouseRepository warehouseRepository,
                             RatingsRepository ratingsRepository,
                             RentalFormRepository rentalFormRepository) {
        this.clientRepository = clientRepository;
        this.globalService = globalService;
        this.warehouseRepository = warehouseRepository;
        this.ratingsRepository = ratingsRepository;
        this.rentalFormRepository = rentalFormRepository;
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
                                                  String warehouseCategory,
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
            address.init(county,town,streetName);
            warehouse = new Warehouse();
            warehouse.init(owner,
                    address,
                    name,
                    squareFeet,
                    temperature,
                    humidityPercent,
                    stockedGoodsType,
                    warehouseCategory,
                    rented);
            warehouseRepository.save(warehouse);
            warehouse = warehouseRepository.findWarehouseByName(name).get();
            owner.CreatedWarehouse(warehouse);
            clientRepository.save(owner);
            warehouseDTO = new WarehouseDTO(warehouse);
        } else {
            throw new WarehouseAlreadyExistsException();
        }
        System.out.println(warehouse);

        Optional<WarehouseDTO> warehouseDTOOpt = Optional.of(warehouseDTO);
        return warehouseDTOOpt;
    }

    public Optional<AgentAndRentFormDTO> agentReferenceByPeriod(Long agentId, LocalDate startDate, LocalDate endDate){

        AgentAndRentFormDTO agentDTO = new AgentAndRentFormDTO();
        isAgentRated(agentDTO,agentId);
        gatherFormData(agentDTO,agentId,startDate,endDate);
        Optional agentDTOOpt = Optional.of(agentDTO);
        for (RentFormDTO form: agentDTO.getRentalForms()
             ) {
            System.out.println("Form" + form);
        }
        return agentDTOOpt;
    }

    public boolean isAgentRated(AgentAndRentFormDTO agentDTO, Long agentId) {
        Agent agent = (Agent)clientRepository.findById(agentId).orElseThrow(
                ()-> new UserNotExististingException()
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

    public void gatherFormData(AgentAndRentFormDTO agentDTO, Long agentId, LocalDate startDate, LocalDate endDate){

       Optional<List<RentalForm>> rentalForms =
               rentalFormRepository.findRentFormsByAgentIdAndStartDateEndDate(agentId,startDate,endDate);


           for (RentalForm form: rentalForms.get()
                ) {

               agentDTO.getRentalForms().add(new RentFormDTO(form.getId(),form.getAgent().getId(),form.getClient().getId(),
                       form.getWarehouse().getId(),form.getStartDate(),form.getEndDate(),form.getRentPerMonth()));
               System.out.println(form);
           }



    }

    public List<Optional<Warehouse>> getWarehouseByOwnerId(Long ownerId) {

        List<Optional<Warehouse>> warehouseOpt = warehouseRepository.findWarehouseByOwnerId(ownerId);
        return warehouseOpt;
    }
}


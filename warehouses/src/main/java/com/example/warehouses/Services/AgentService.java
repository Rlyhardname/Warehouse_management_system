package com.example.warehouses.Services;

import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.DTO.RentFormDTO;
import com.example.warehouses.Exception.Client.AgentNotAssignedWarehouseException;
import com.example.warehouses.Exception.Client.UserNotExististingException;
import com.example.warehouses.Exception.Warehouse.AlreadyRentedException;
import com.example.warehouses.Exception.Warehouse.WarehouseNotExistingException;
import com.example.warehouses.Model.User.Agent;
import com.example.warehouses.Model.User.User;
import com.example.warehouses.Model.User.Owner;
import com.example.warehouses.Model.warehouse.RentalForm;
import com.example.warehouses.Model.warehouse.Warehouse;
import com.example.warehouses.Repository.*;
import com.example.warehouses.util.AgentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AgentService {

    private final UsersRepository usersRepository;
    private final UsersService globalService;
    private final WarehouseRepository warehouseRepository;
    private final RatingsRepository ratingsRepository;
    private final RentalFormRepository rentalFormRepository;
    private final WarehouseAssignedToAgentRepository marketRepository;

    private final AddressRepository addressRepository;
    private final WarehouseAssignedToAgentRepository warehouseAssignedToAgentRepository;

    @Autowired
    public AgentService(UsersRepository usersRepository,
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

    public AgentAndRentFormDTO getAgentContractsAndRatingsByPeriod(Long agentId, LocalDate startDate, LocalDate endDate) {

        AgentAndRentFormDTO agentDTO = new AgentAndRentFormDTO();
        // TODO change return type or use it somehow?
        AgentUtil.isAgentRated(usersRepository, ratingsRepository, agentDTO, agentId);
        AgentUtil.gatherFormData(rentalFormRepository, agentDTO, agentId, startDate, endDate);
        for (RentFormDTO form : agentDTO.getRentalForms()
        ) {
            System.out.println("Form" + form);
        }
        return agentDTO;
    }

    public ResponseEntity<String> rentWarehouse(Long ownerId,
                                        Long agentId,
                                        Long clientId,
                                        Long warehouseId,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        double contractFiatWorth,
                                        double agentFee) {


        Owner owner = (Owner) usersRepository.findById(ownerId).orElseThrow(
                () -> new UserNotExististingException()
        );
        Agent agent = (Agent) usersRepository.findById(agentId).orElseThrow(
                () -> new UserNotExististingException()
        );
        User user = usersRepository.findById(clientId).orElseThrow(
                () -> new UserNotExististingException()
        );
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(
                () -> new WarehouseNotExistingException()
        );
        if (warehouse.isRented() == true) throw new AlreadyRentedException();

        RentalForm contract = AgentUtil.createContract(agent,
                user,
                warehouse,
                startDate,
                endDate,
                contractFiatWorth,
                agentFee);

        if (!warehouseAssignedToAgentRepository.findByAgentIdAndWarehouseId(agentId, warehouseId).isPresent()) {
            throw new AgentNotAssignedWarehouseException();
        }
        rentalFormRepository.save(contract);
        warehouseAssignedToAgentRepository.updateStatus("CONTRACTED", agentId, warehouseId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}

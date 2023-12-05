package com.example.warehouses.services;

import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.exception.Client.AgentNotAssignedWarehouseException;
import com.example.warehouses.exception.Client.UserNotExististingException;
import com.example.warehouses.exception.Warehouse.AlreadyRentedException;
import com.example.warehouses.exception.Warehouse.WarehouseNotExistingException;
import com.example.warehouses.model.user.User;
import com.example.warehouses.model.warehouse.RentalForm;
import com.example.warehouses.model.warehouse.Warehouse;
import com.example.warehouses.repository.*;
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

        return agentDTO;
    }

    public ResponseEntity<String> rentWarehouse(Long ownerId,
                                                Long agentId,
                                                Long customerId,
                                                Long warehouseId,
                                                LocalDate startDate,
                                                LocalDate endDate,
                                                double contractFiatWorth,
                                                double agentFee) {
        if (!usersRepository.existsById(ownerId)) {
            throw new UserNotExististingException();
        }

        User agent = usersRepository.findById(agentId).orElseThrow(
                () -> new UserNotExististingException()
        );
        User customer = usersRepository.findById(customerId).orElseThrow(
                () -> new UserNotExististingException()
        );
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(
                () -> new WarehouseNotExistingException()
        );
        if (warehouse.isRented() == true) throw new AlreadyRentedException();

        RentalForm contract = AgentUtil.createContract(agent,
                customer,
                warehouse,
                startDate,
                endDate,
                contractFiatWorth,
                agentFee);
        if (!warehouseAssignedToAgentRepository.existsById_AgentIdAndId_WarehouseId(agentId, warehouseId)) {
            throw new AgentNotAssignedWarehouseException();
        }

        rentalFormRepository.save(contract);
        warehouseAssignedToAgentRepository.updateStatus("CONTRACTED", agentId, warehouseId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}

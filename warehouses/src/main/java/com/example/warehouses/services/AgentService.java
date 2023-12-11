package com.example.warehouses.services;

import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.DTO.RentFormDTO;
import com.example.warehouses.exception.Client.AgentNotAssignedWarehouseException;
import com.example.warehouses.exception.Client.UserNotExististingException;
import com.example.warehouses.exception.Warehouse.AlreadyRentedException;
import com.example.warehouses.exception.Warehouse.WarehouseNotExistingException;
import com.example.warehouses.model.AgentRatings;
import com.example.warehouses.model.user.UserImpl;
import com.example.warehouses.model.warehouse.RentalForm;
import com.example.warehouses.model.warehouse.Warehouse;
import com.example.warehouses.repository.*;
import com.example.warehouses.util.AgentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        // TODO change return type or use it somehow?
        UserImpl userImpl = usersRepository.findById(agentId).orElseThrow(() -> {
            new UserNotExististingException();
            return null;
        });

        if (!userImpl.getDType().equals("agent")) {
            return null;
        }

        List<RentalForm> allAgentRentalForms = AgentUtil.fetchRentalFormsList(rentalFormRepository, agentId, startDate, endDate);
        List<AgentRatings> ratings = new ArrayList<>();
        if (AgentUtil.isAgentRated(agentId, ratingsRepository)) {
            ratings = new ArrayList<>(AgentUtil.fetchRatingsList(agentId, ratingsRepository));
        }

        double totalStars = AgentUtil.totalStars(ratings);
        List<RentFormDTO> rentalFormDTO = AgentUtil.createRentalFormDTO(allAgentRentalForms);
        AgentAndRentFormDTO agentAndRentFormDTO = new AgentAndRentFormDTO(
                userImpl.getFirstName(),
                userImpl.getLastName(),
                userImpl.getEmail(),
                totalStars,
                ratings.size(),
                rentalFormDTO);

        return agentAndRentFormDTO;
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

        UserImpl agent = usersRepository.findById(agentId).orElseThrow(
                () -> new UserNotExististingException()
        );
        UserImpl customer = usersRepository.findById(customerId).orElseThrow(
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

        return new ResponseEntity<>("Successfully rented out!", HttpStatus.ACCEPTED);
    }

}

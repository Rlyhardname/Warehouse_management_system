package com.example.warehouses.controller.user;

import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.services.AgentService;
import jakarta.validation.constraints.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/main/agents")
public class AgentController {
    private final AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @SneakyThrows
    @GetMapping("/{agent_id}")
    // TODO Maybe redesign to post?
    public AgentAndRentFormDTO getAgentContractsAndRatingsByPeriod(@PathVariable @Min(value = 1) Long agent_id) {
        LocalDate startDate = LocalDate.of(2020, 5, 25);
        LocalDate endDate = LocalDate.of(2023, 6, 26);

        return agentService.getAgentContractsAndRatingsByPeriod(agent_id, startDate, endDate);
    }

    @PostMapping("/rent-warehouse")
    public ResponseEntity<String> rentWarehouse(@RequestParam @Min(value = 1) Long ownerId,
                                                @RequestParam @Min(value = 1) Long agentId,
                                                @RequestParam @Min(value = 1) Long clientId,
                                                @RequestParam @Min(value = 1) Long warehouseId,
                                                @RequestParam
                                                @Future(message = "Date must be in the future")
                                                @NotNull(message = "Date cannot be left blank")
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                @RequestParam
                                                @Future(message = "Date must be in the future")
                                                @NotNull(message = "Date cannot be left blank")
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                @RequestParam @DecimalMin("0.1") double contractFiatWorth,
                                                @RequestParam @DecimalMin("0.1") @DecimalMax("5.0") double agentFee) {

        return agentService.rentWarehouse(ownerId,
                agentId,
                clientId,
                warehouseId,
                startDate,
                endDate,
                contractFiatWorth,
                agentFee);
    }
}

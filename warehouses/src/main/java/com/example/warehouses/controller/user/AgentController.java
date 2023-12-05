package com.example.warehouses.controller.user;

import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.services.AgentService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AgentAndRentFormDTO getAgentContractsAndRatingsByPeriod(@PathVariable Long agent_id) {
        LocalDate startDate = LocalDate.of(2020, 5, 25);
        LocalDate endDate = LocalDate.of(2023, 6, 26);

        return agentService.getAgentContractsAndRatingsByPeriod(agent_id, startDate, endDate);
    }

    @PostMapping("/rent-warehouse")
    public ResponseEntity<String> rentWarehouse(@RequestParam Long ownerId,
                                                @RequestParam Long agentId,
                                                @RequestParam Long clientId,
                                                @RequestParam Long warehouseId,
                                                @RequestParam LocalDate startDate,
                                                @RequestParam LocalDate endDate,
                                                @RequestParam double contractFiatWorth,
                                                @RequestParam double agentFee) {

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

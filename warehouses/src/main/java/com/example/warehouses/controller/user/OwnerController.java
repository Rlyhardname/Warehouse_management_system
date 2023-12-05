package com.example.warehouses.controller.user;

import com.example.warehouses.configurations.Enum.WarehouseCategory;
import com.example.warehouses.DTO.AgentDTO;
import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.model.AgentRatings;
import com.example.warehouses.model.user.Agent;
import com.example.warehouses.model.warehouse.Warehouse;
import com.example.warehouses.services.OwnerService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Validated
@RequestMapping(path = "/api/main/owners")
public class OwnerController {
    private ExecutorService executorService;
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService clientFuncService) {
        this.ownerService = clientFuncService;
    }

    @PostConstruct
    public void Init() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @PreDestroy
    public void closeCustom() {
        executorService.shutdown();
    }

    // Users

    @GetMapping("/warehouses/{owner_id}")
    public List<WarehouseDTO> getAllWarehousesOwnedBy(@PathVariable @Min(value = 0, message = "Invalid id, input number is" +
            "lower than 1 or not a hall number") Long owner_id) {
        System.out.println("hmm?");
        List<WarehouseDTO> warehouseList = new ArrayList<>();
        Optional<List<Warehouse>> warehousesOpt = ownerService.getWarehouseByOwnerId(owner_id);
        if (warehousesOpt.isPresent()) {
            for (Warehouse warehouse : warehousesOpt.get()
            ) {
                warehouseList.add((new WarehouseDTO(warehouse)));
            }
        }
        return warehouseList;
    }


    // not finished...
    @GetMapping("/agents/remove/{id}")
    public Set<AgentDTO> RemoveAgentsFromWarehouse(@PathVariable Long... id) {
        System.out.println(Arrays.toString(id));
        List<Long> agentIds = List.of(id);
        Set<AgentDTO> agentsLeftDTO = ownerService.RemoveAgentsFromWarehouse(2L, agentIds, 1L);
        return agentsLeftDTO;
    }

    @GetMapping("/market/warehouse")
    public Set<Agent> setAgentsToWarehouse() { // , List<Warehouse> warehouses, List<Agent> agents Long owner Id@RequestParam Long ownerId
        //Long ownerId, Long warehouseId, List<Long> agentIds

        List<Long> agentIdss = List.of(1L, 2L, 3L, 4L);

        return ownerService.setAgentsToWarehouse(2L, agentIdss, 2L);
    }

    @GetMapping("//agents")
    public Set<AgentDTO> getAllAgents() {
        return ownerService.getAllAgents();
    }


    @PostMapping("/create/warehouse")
    public WarehouseDTO createWarehouse(
            @RequestParam(value = "owner") String email,
            @RequestParam(value = "county") String county,
            @RequestParam(value = "town") String town,
            @RequestParam(value = "streetName") String streetName,
            @RequestParam(value = "warehouseName") String name,
            @RequestParam(value = "squareFeet") String squareFeet,
            @RequestParam(value = "temperature") String temperature,
            @RequestParam(value = "humidityPercent") String humidityPercent,
            @RequestParam(value = "stockedGoodsType") String stockedGoodsType,
            @RequestParam(value = "warehouseCategory") String warehouseCategory,
            @RequestParam(value = "rented") String rented) {

        WarehouseCategory category = ownerService.warehouseCategory(warehouseCategory);

        return ownerService.createWarehouse(
                email,
                county,
                town,
                streetName,
                name,
                squareFeet,
                temperature,
                humidityPercent,
                stockedGoodsType,
                category,
                rented);
        // possibly return a view with a list of agents to pick from.
    }

    // TODO
    // edit/warehouses func() { return edit page view from which to send new post request
    //  to make individual agent or list of agents pick to represent it - > edit/warehouse/agents}

    // TODO
    // edit/warehouse/agents func() { put - add/remove agents from db }


    @GetMapping("rented-warehouses/{status}")
    public Optional<List<WarehouseDTO>> getWarehouseByStatus(@PathVariable(required = false) String status) {
        List<WarehouseDTO> warehouseDTOlist = new ArrayList<>();
        for (Warehouse warehouse : ownerService.getAllWarehouses(status).get()
        ) {
            warehouseDTOlist.add(new WarehouseDTO(warehouse));
        }

        return Optional.of(warehouseDTOlist);
    }

    @GetMapping("/market/{ownerId}")
    public List<Warehouse> fetchWarehouses(@PathVariable Long ownerId) { //
        List<Warehouse> allOwnerWarehouses = ownerService.fetchWarehouses(ownerId);
        System.out.println(allOwnerWarehouses.size());
        ModelAndView modelAndView = new ModelAndView("/main/testFetch");
        modelAndView.addObject("dataList", allOwnerWarehouses);
        return allOwnerWarehouses;
    }

    @PostMapping("rate-agent")
    public List<AgentRatings> rateAgent(@RequestParam Long ownerId,
                                        @RequestParam Long agentId,
                                        @RequestParam int stars) {

        return ownerService.rateAgent(ownerId, agentId, stars);
    }

}

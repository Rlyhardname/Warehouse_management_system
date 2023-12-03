package com.example.warehouses.controller;

import com.example.warehouses.Configurations.Enum.WarehouseCategory;
import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.DTO.AgentDTO;
import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.Exception.Client.ClientAlreadyRegisteredException;
import com.example.warehouses.Model.AgentRatings;
import com.example.warehouses.Model.User.Agent;
import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Model.warehouse.Warehouse;
import com.example.warehouses.Services.OwnerService;
import com.example.warehouses.Services.ClientService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Validated
@RequestMapping(path = "/api/main/owners")
public class OwnerController {

    private ExecutorService executorService;
    private final OwnerService ownerService;
    private final ClientService clientService;

    @Autowired
    public OwnerController(OwnerService clientFuncService,
                           ClientService globalService) {


        this.ownerService = clientFuncService;
        this.clientService = globalService;
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

    @GetMapping("warehouses/{owner_id}")
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
    @GetMapping("agents/remove/{id}")
    public Set<AgentDTO> RemoveAgentsFromWarehouse(@PathVariable Long... id) {
        System.out.println(Arrays.toString(id));
        List<Long> agentIds = List.of(id);
        Set<AgentDTO> agentsLeftDTO = ownerService.RemoveAgentsFromWarehouse(2L, agentIds, 1L);
        return agentsLeftDTO;
    }

    @GetMapping("market/warehouse")
    public Set<Agent> setAgentsToWarehouse() { // , List<Warehouse> warehouses, List<Agent> agents Long owner Id@RequestParam Long ownerId
        //Long ownerId, Long warehouseId, List<Long> agentIds

        List<Long> agentIdss = List.of(1L, 2L, 3L, 4L);

        return ownerService.setAgentsToWarehouse(2L, agentIdss, 2L);
    }

    @GetMapping("agents")
    public Set<AgentDTO> getAllAgents() {
        return ownerService.getAllAgents();
    }

    @PostMapping("agent-form")
    public AgentAndRentFormDTO returnDataToClient(AgentAndRentFormDTO object) {

        return object;
    }

    @SneakyThrows
    @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )//"application/x-www-form-urlencoded")
    public ResponseEntity<String> registerClient(@Valid @ModelAttribute Client client,
                                                 HttpServletResponse response) {
        if (!clientService.isUsernameTaken(client.getEmail())) {
            ownerService.register(client.getEmail(), client.getPassword(), client.getFirstName(), client.getLastName(), client.getAccountType(), response);
            return new ResponseEntity<>("User Successfully registered!", HttpStatus.ACCEPTED);
        } else {
            throw new ClientAlreadyRegisteredException();
        }
    }

    @PostMapping("create/warehouse")
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

        WarehouseCategory category = clientService.warehouseCategory(warehouseCategory);

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


    @PostMapping("rentwarehouse")
    public ResponseEntity<String> rentWarehouse(@RequestParam Long ownerId,
                                                @RequestParam Long agentId,
                                                @RequestParam Long clientId,
                                                @RequestParam Long warehouseId,
                                                @RequestParam LocalDate startDate,
                                                @RequestParam LocalDate endDate,
                                                @RequestParam double contractFiatWorth,
                                                @RequestParam double agentFee,
                                                HttpServletResponse response
    ) {

        Runnable runnable = () -> ownerService.rentWarehouse(ownerId,
                agentId,
                clientId,
                warehouseId,
                startDate,
                endDate,
                contractFiatWorth,
                agentFee);
        executorService.submit(runnable);

        return new ResponseEntity<>("Server computing request", HttpStatus.ACCEPTED);
    }








    @GetMapping("rented-warehouses/{status}")
    public Optional<List<WarehouseDTO>> getWarehouseByStatus(@PathVariable(required = false) String status) {
        List<WarehouseDTO> warehouseDTOlist = new ArrayList<>();
        for (Warehouse warehouse : ownerService.getAllWarehouses(status).get()
        ) {
            warehouseDTOlist.add(new WarehouseDTO(warehouse));
        }

        return Optional.of(warehouseDTOlist);
    }

    @GetMapping("market/{ownerId}")
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

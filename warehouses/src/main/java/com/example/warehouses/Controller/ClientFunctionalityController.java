package com.example.warehouses.Controller;

import com.example.warehouses.Configurations.Enum.WarehouseCategory;
import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.DTO.AgentDTO;
import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.Exception.Client.ClientAlreadyRegisteredException;
import com.example.warehouses.Model.AgentRatings;
import com.example.warehouses.Model.User.Agent;
import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Model.warehouse.Address;
import com.example.warehouses.Model.warehouse.Warehouse;
import com.example.warehouses.Services.ClientFuncService;
import com.example.warehouses.Services.GlobalService;

import javax.annotation.Resource;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@Validated
@RequestMapping(path = "/api/main/")
public class ClientFunctionalityController {

    private ExecutorService executorService;
    private final ClientFuncService clientFuncService;
    private final GlobalService globalService;

    @Autowired
    public ClientFunctionalityController(ClientFuncService clientFuncService,
                                         GlobalService globalService) {

        this.clientFuncService = clientFuncService;
        this.globalService = globalService;
    }

    @PostConstruct
    public void Init(){
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @PreDestroy
    public void closeCustom(){
        executorService.shutdown();
    }

    @GetMapping("")
    public static String visualizeMainPage() {
        return "Hello World";
    }

    @SneakyThrows
    @PostMapping("createClient")
    public Client registerClient(@RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String clientType,
                                 HttpServletResponse response) {

        Client client;
        if (!globalService.isUsernameTaken(email)) {
            client = clientFuncService.register(email, password, firstName, lastName, clientType, response);
            response.sendRedirect("http://localhost:8080/");
        } else {
            throw new ClientAlreadyRegisteredException();
        }

        return client;
    }


    @PostMapping("createWarehouse")
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

        WarehouseCategory category = globalService.warehouseCategory(warehouseCategory);

        return clientFuncService.createWarehouse(
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
    }


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
        Runnable runnable = () -> clientFuncService.rentWarehouse(ownerId,
                agentId,
                clientId,
                warehouseId,
                startDate,
                endDate,
                contractFiatWorth,
                agentFee);
        executorService.submit(runnable);

        return new ResponseEntity<>("Server computing request",HttpStatus.ACCEPTED);
    }

    @SneakyThrows
    @GetMapping("agentDTO/{agent_id}")
    public AgentAndRentFormDTO getAgentContractsAndRatingsByPeriod(@PathVariable Long agent_id) {
        LocalDate startDate = LocalDate.of(2020, 5, 25);
        LocalDate endDate = LocalDate.of(2023, 6, 26);
        return clientFuncService.getAgentContractsAndRatingsByPeriod(agent_id, startDate, endDate);
    }

    @PostMapping("response")
    public AgentAndRentFormDTO returnDataToClient(AgentAndRentFormDTO object){

        return object;
    }

    @GetMapping("getwarehousedto/{owner_id}")
    public List<WarehouseDTO> getAllWarehousesOwnedBy(@PathVariable Long owner_id) {
        System.out.println("hmm?");
        List<WarehouseDTO> warehouseList = new ArrayList<>();
        Optional<List<Warehouse>> warehousesOpt  = clientFuncService.getWarehouseByOwnerId(owner_id);
        if (warehousesOpt.isPresent()) {
            for (Warehouse warehouse : warehousesOpt.get()
            ) {
                warehouseList.add((new WarehouseDTO(warehouse)));
            }
        }
        return warehouseList;
    }

    @GetMapping("getwarehouses/{status}")
    public Optional<List<WarehouseDTO>> getWarehouseByStatus(@PathVariable(required = false) String status) {
        List<WarehouseDTO> warehouseDTOlist = new ArrayList<>();
        for (Warehouse warehouse : clientFuncService.getAllWarehouses(status).get()
        ) {
            warehouseDTOlist.add(new WarehouseDTO(warehouse));
        }

        return Optional.of(warehouseDTOlist);
    }

    @GetMapping("market/{ownerId}")
    public List<Warehouse> fetchWarehouses(@PathVariable Long ownerId) { //
        List<Warehouse> allOwnerWarehouses = clientFuncService.fetchWarehouses(ownerId);
        System.out.println(allOwnerWarehouses.size());
        ModelAndView modelAndView = new ModelAndView("/main/testFetch");
        modelAndView.addObject("dataList", allOwnerWarehouses);
        return allOwnerWarehouses;
    }

    @GetMapping ("market/warehouse")
    public Set<Agent> setAgentsToWarehouse() { // , List<Warehouse> warehouses, List<Agent> agents Long owner Id@RequestParam Long ownerId
        //Long ownerId, Long warehouseId, List<Long> agentIds

        List<Long> agentIdss = List.of(1L, 2L, 3L, 4L);

        return clientFuncService.setAgentsToWarehouse(2L, agentIdss, 2L);
    }

    @GetMapping("removeagents")
    public Set<AgentDTO> RemoveAgentsFromWarehouse() {

        List<Long> agentIdss = List.of(4L);
        Set<AgentDTO> agentsLeftDTO = clientFuncService.RemoveAgentsFromWarehouse(2L, agentIdss, 1L);
        return agentsLeftDTO;

    }

    @PostMapping("rateagent")
    public List<AgentRatings> rateAgent(@RequestParam Long ownerId,
                                        @RequestParam Long agentId,
                                        @RequestParam int stars) {

        return clientFuncService.rateAgent(ownerId, agentId, stars);
    }

    @GetMapping("getAllAgents")
    public Set<AgentDTO> getAllAgents() {
        return clientFuncService.getAllAgents();
    }

    @PostMapping("test")
    public ResponseEntity<String> test(@RequestParam Long ownerId,
                                       @RequestParam Long agentId){

        if(ownerId!=null && agentId!=null){
            return new ResponseEntity<>("Success",HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad params or smth went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

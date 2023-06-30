package com.example.warehouses.Controller;

import com.example.warehouses.Configurations.Enum.WarehouseCategory;
import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.DTO.AgentDTO;
import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.Model.User.Agent;
import com.example.warehouses.Model.warehouse.Address;
import com.example.warehouses.Model.warehouse.Warehouse;
import com.example.warehouses.Services.ClientFuncService;
import com.example.warehouses.Services.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@Validated
@RequestMapping(path = "main")
public class ClientFunctionalityController {

    private final ClientFuncService clientFuncService;
    private final GlobalService globalService;

    @Autowired
    public ClientFunctionalityController(ClientFuncService clientFuncService,
                                         GlobalService globalService) {
        this.clientFuncService = clientFuncService;
        this.globalService = globalService;
    }

    @GetMapping("")
    public String visualizeMainPage() {
        return "HelloWorld";
    }


    @PostMapping("/createWarehouse")
    public Optional<WarehouseDTO> createWarehouse(
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

    @GetMapping("/agentDTO")
    public AgentAndRentFormDTO getAgentContractsAndRatingsByPeriod(){
        LocalDate startDate = LocalDate.of(2022,5,25);
        LocalDate endDate = LocalDate.of(2022,6,26);
        long testAgent = 4L;
        return clientFuncService.getAgentContractsAndRatingsByPeriod(4L,startDate,endDate);
    }

    @GetMapping("/getWarehouseDTO/")
    public List<Optional<WarehouseDTO>> getAllWarehousesOwnedBy(){ // @RequestParam String ownerId
        List<Optional<WarehouseDTO>> warehouseDTOOpt = new ArrayList<>();
        System.out.println(2L); // Long.getLong(ownerId)
        if(!clientFuncService.getWarehouseByOwnerId(2L).isEmpty()){ //Long.getLong(ownerId)
            for (Warehouse warehouse : clientFuncService.getWarehouseByOwnerId(2L).get()//Long.getLong(ownerId);
                     ) {
                        warehouseDTOOpt.add(Optional.of(new WarehouseDTO(warehouse)));
                }
        }


        return warehouseDTOOpt;
    }

    @GetMapping("/getwarehouses/{status}")
    public Optional<List<WarehouseDTO>> getWarehouseByStatus(@PathVariable(required = false) String status){
        List<WarehouseDTO> warehouseDTOlist = new ArrayList<>();
        for (Warehouse warehouse: clientFuncService.getAllWarehouses(status).get()
             ) {
            warehouseDTOlist.add(new WarehouseDTO(warehouse));
        }

        return Optional.of(warehouseDTOlist);
    }

    @GetMapping("/market/{ownerId}")
    public ModelAndView fetchWarehouses(@PathVariable Long ownerId){
        List<Warehouse> allOwnerWarehouses = clientFuncService.fetchWarehouses(ownerId);
        System.out.println(allOwnerWarehouses.size());
        ModelAndView modelAndView = new ModelAndView("main/Warehouses");
        modelAndView.addObject("dataList",allOwnerWarehouses);
        return modelAndView;
    }
    @PostMapping("/market/warehouse")
    public Set<Agent> setAgentsToWarehouse(Long ownerId, Long warehouseId, List<Long> agentIds){ // , List<Warehouse> warehouses, List<Agent> agents Long owner Id@RequestParam Long ownerId

        List<Long> agentIdss = List.of(1L,2L,3L,4L);

        return  clientFuncService.setAgentsToWarehouse(3L, agentIdss, 1L);
    }

    @GetMapping("main/removeagents")
    public Set<AgentDTO> RemoveAgentsFromWarehouse(){

        List<Long> agentIdss = List.of(1L,2L,3L,4L);
        Set<AgentDTO> agentsLeftDTO = clientFuncService.RemoveAgentsFromWarehouse(2L,agentIdss,1L);
        return agentsLeftDTO;

    }

    @GetMapping("/zzz")
    public ModelAndView myPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Test"); // Name of your HTML template file without the extension
        Address address = new Address();
        address.init("Varna","Varna", "HelloWorldSTREET");
        modelAndView.addObject("jsonData",address);
        return modelAndView;
    }



}

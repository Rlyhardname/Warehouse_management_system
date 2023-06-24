package com.example.warehouses.Controller;

import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.Model.Warehouse;
import com.example.warehouses.Services.ClientFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "main")
public class ClientFunctionalityController {

    private final ClientFuncService clientFuncService;

    @Autowired
    public ClientFunctionalityController(ClientFuncService clientFuncService) {
        this.clientFuncService = clientFuncService;
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
                warehouseCategory,
                rented);
    }

    @GetMapping("/agentDTO")
    public Optional<AgentAndRentFormDTO> agentReferenceByPeriod(){
        LocalDate startDate = LocalDate.of(2022,5,25);
        LocalDate endDate = LocalDate.of(2022,6,26);
        long testAgent = 4L;
        return clientFuncService.agentReferenceByPeriod(4L,startDate,endDate);
    }

    @GetMapping("/getWarehouseDTO")
    public List<Optional<WarehouseDTO>> getAllWarehousesOwnedBy(Long ownerId){
        List<Optional<WarehouseDTO>> warehouseDTOOpt = new ArrayList<>();
        if(!clientFuncService.getWarehouseByOwnerId(2L).isEmpty()){
            for (Optional<Warehouse> warehouse : clientFuncService.getWarehouseByOwnerId(2L)
                     ) {
                        warehouseDTOOpt.add(Optional.of(new WarehouseDTO(warehouse.get())));
                }
        }


        return warehouseDTOOpt;
    }

}

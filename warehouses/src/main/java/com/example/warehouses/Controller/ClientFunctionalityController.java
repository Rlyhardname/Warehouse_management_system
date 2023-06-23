package com.example.warehouses.Controller;

import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.Model.Warehouse;
import com.example.warehouses.Repository.ClientRepository;
import com.example.warehouses.Services.ClientFuncService;
import com.example.warehouses.Services.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(value = "warehouseName") String name,
            @RequestParam(value = "squareFeet") String squareFeet,
            @RequestParam(value = "temperature") String temperature,
            @RequestParam(value = "humidityPercent") String humidityPercent,
            @RequestParam(value = "stockedGoodsType") String stockedGoodsType,
            @RequestParam(value = "warehouseCategory") String warehouseCategory,
            @RequestParam(value = "rented") String rented) {

        return clientFuncService.createWarehouse(
                email,
                name,
                squareFeet,
                temperature,
                humidityPercent,
                stockedGoodsType,
                warehouseCategory,
                rented);
    }

}

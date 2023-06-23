package com.example.warehouses.Services;

import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.Exception.UserNotExististingException;
import com.example.warehouses.Exception.WarehouseAlreadyExistsException;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.Owner;
import com.example.warehouses.Model.Warehouse;
import com.example.warehouses.Repository.ClientRepository;
import com.example.warehouses.Repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class ClientFuncService {

    private final ClientRepository clientRepository;
    private final GlobalService globalService;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public ClientFuncService(ClientRepository clientRepository, GlobalService globalService, WarehouseRepository warehouseRepository) {
        this.clientRepository = clientRepository;
        this.globalService = globalService;
        this.warehouseRepository = warehouseRepository;
    }

    public Optional<WarehouseDTO> createWarehouse(String email,
                                                  String name,
                                                  String squareFeet,
                                                  String temperature,
                                                  String humidityPercent,
                                                  String stockedGoodsType,
                                                  String warehouseCategory,
                                                  String rented) {
        System.out.println(email + " " + name);
        Warehouse warehouse = null;
        WarehouseDTO warehouseDTO = null;
        Optional<Warehouse> warehouseOpt = warehouseRepository.findWarehouseByName(name);
        Optional<Client> ownerOpt = Optional.of(clientRepository.findClientByEmail(email).orElseThrow(
                () -> new UserNotExististingException()
        ));
        if (warehouseOpt.isPresent() == false) {
            Owner owner = (Owner) ownerOpt.get();
            warehouse = new Warehouse();
            warehouse.init(owner,
                    name,
                    squareFeet,
                    temperature,
                    humidityPercent,
                    stockedGoodsType,
                    warehouseCategory,
                    rented);
            warehouseRepository.save(warehouse);
            warehouse = warehouseRepository.findWarehouseByName(name).get();
            owner.CreatedWarehouse(warehouse);
            clientRepository.save(owner);
            warehouseDTO = new WarehouseDTO(warehouse);
        } else{
           throw new WarehouseAlreadyExistsException();
        }
        System.out.println(warehouse);

        Optional<WarehouseDTO> warehouseDTOOpt = Optional.of(warehouseDTO);
        return warehouseDTOOpt;
    }

}


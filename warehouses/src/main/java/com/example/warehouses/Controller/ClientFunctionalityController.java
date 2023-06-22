package com.example.warehouses.Controller;

import com.example.warehouses.DTO.WarehouseDTO;
import com.example.warehouses.Exception.UserNotExististingException;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.Owner;
import com.example.warehouses.Model.Warehouse;
import com.example.warehouses.Repository.ClientRepository;
import com.example.warehouses.Repository.WarehouseRepository;
import com.example.warehouses.Services.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path="main")
public class ClientFunctionalityController {

    private final ClientRepository clientRepository;
    private final WarehouseRepository warehouseRepository;
    private final GlobalService globalService;

    @Autowired
    public ClientFunctionalityController(ClientRepository clientRepository,
                                         GlobalService globalService,
                                         WarehouseRepository warehouseRepository) {
        this.clientRepository = clientRepository;
        this.globalService = globalService;
        this.warehouseRepository = warehouseRepository;
    }

    @GetMapping("")
    public String visualizeMainPage()
    {
        return "HelloWorld";
    }

    @PostMapping("/createWarehouse")
    public Optional<WarehouseDTO> createWarehouse(@RequestParam(value="owner") String email ,
                                               @RequestParam(value="warehouseName") String name){
        System.out.println(email + " " +  name);
        Warehouse warehouse;
        WarehouseDTO warehouseDTO = null;
      Optional<Warehouse> warehouseOpt = warehouseRepository.findWarehouseByName(name);
      Optional<Client> ownerOpt = Optional.of(clientRepository.findClientByEmail(email).orElseThrow(
              ()-> new UserNotExististingException()
      ));
      if(warehouseOpt.isPresent() == false){
          Owner owner = (Owner)ownerOpt.get();
          warehouse = new Warehouse();
          warehouse.init(owner,name);
          warehouseRepository.save(warehouse);
          warehouse = warehouseRepository.findWarehouseByName(name).get();
          owner.CreatedWarehouse(warehouse);
          warehouseDTO = new WarehouseDTO(warehouse);
          for (Warehouse item: owner.getWarehousesOwned()) {
              System.out.println(item);
          }
      }
      Optional<WarehouseDTO> warehouseDTOOpt = Optional.of(warehouseDTO);

    return warehouseDTOOpt;
    }

}

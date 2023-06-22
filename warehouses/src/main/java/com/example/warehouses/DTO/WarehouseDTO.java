package com.example.warehouses.DTO;

import com.example.warehouses.Model.Warehouse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WarehouseDTO {

    private final Long id;
    private final String warehouseName;

    public WarehouseDTO(Warehouse warehouse){

    id = warehouse.getId();
    warehouseName = warehouse.getWarehouseName();

    }

}

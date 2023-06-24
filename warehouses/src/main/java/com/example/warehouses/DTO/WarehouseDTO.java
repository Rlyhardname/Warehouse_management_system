package com.example.warehouses.DTO;

import com.example.warehouses.Model.Address;
import com.example.warehouses.Model.Warehouse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WarehouseDTO {

    private final Long id;
    private final Address address;
    private final String warehouseName;
    private final String squareFeet;
    private final String temperature;
    private final String humidityPercent; // could combine the two into a class/state or remove one of them;
    private final String stockedGoodsType;
    private final String warehouseCategory;
    private final boolean rented;

    public WarehouseDTO(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.address = warehouse.getAddress();
        this.warehouseName = warehouse.getWarehouseName();
        this.squareFeet = warehouse.getSquareFeet();
        this.temperature = warehouse.getTemperature();
        this.humidityPercent = warehouse.getHumidityPercent();
        this.stockedGoodsType = warehouse.getStockedGoodsType();
        this.warehouseCategory = warehouse.getWarehouseCategory();
        this.rented = warehouse.isRented();
    }
}

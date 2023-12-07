package com.example.warehouses.model.warehouse;

import com.example.warehouses.model.user.Owner;

public class WarehouseBuilderImpl implements WarehouseBuilder {

    private final Warehouse warehouse;

    public WarehouseBuilderImpl(Warehouse warehouse) {
        this.warehouse = warehouse;
    }


    @Override
    public WarehouseBuilder owner(Owner owner) {
        this.warehouse.setOwner(owner);
        return this;
    }

    @Override
    public WarehouseBuilder name(String name) {
        warehouse.setName(name);
        return this;
    }

    @Override
    public WarehouseBuilder area(Double squareFeet) {
        warehouse.setSquareFeet(squareFeet);
        return this;
    }

    @Override
    public WarehouseBuilder celsiusTemp(Double temp) {
        warehouse.setTemperature(temp);
        return this;
    }

    @Override
    public WarehouseBuilder humidityPercent(Double percent) {
        warehouse.setHumidityPercent(percent);
        return this;
    }

    @Override
    public WarehouseBuilder inventory(String inventory) {
        warehouse.setInventory(inventory);
        return this;
    }

    @Override
    public WarehouseBuilder category(String category) {
        warehouse.setCategory(category);
        return this;
    }

    @Override
    public WarehouseBuilder country(String country) {
        warehouse.getAddress().setCounty(country);
        return this;
    }

    @Override
    public WarehouseBuilder town(String town) {
        warehouse.getAddress().setTown(town);
        return this;
    }

    @Override
    public WarehouseBuilder street(String street) {
        warehouse.getAddress().setStreetName(street);
        return this;
    }

    @Override
    public Warehouse build() {
        return warehouse;
    }
}

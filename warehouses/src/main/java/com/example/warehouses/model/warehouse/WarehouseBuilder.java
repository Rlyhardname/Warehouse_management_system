package com.example.warehouses.model.warehouse;

import com.example.warehouses.model.user.Owner;

public interface WarehouseBuilder {

    public WarehouseBuilder owner(Owner owner);

    public WarehouseBuilder name(String name);

    public WarehouseBuilder area(Double squareFeet);

    public WarehouseBuilder celsiusTemp(Double temp);

    public WarehouseBuilder humidityPercent(Double percent);

    public WarehouseBuilder inventory(String inventory);

    public WarehouseBuilder category(String category);

    public WarehouseBuilder country(String country);

    public WarehouseBuilder town(String town);

    public WarehouseBuilder street(String street);

    public Warehouse build();


}

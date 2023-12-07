package com.example.warehouses.model.warehouse;

import com.example.warehouses.model.user.Owner;

public interface WarehouseBuilder {

    WarehouseBuilder owner(Owner owner);

    WarehouseBuilder name(String name);

    WarehouseBuilder area(Double squareFeet);

    WarehouseBuilder celsiusTemp(Double temp);

    WarehouseBuilder humidityPercent(Double percent);

    WarehouseBuilder inventory(String inventory);WarehouseBuilder category(String category);

    WarehouseBuilder country(String country);

    WarehouseBuilder town(String town);

    WarehouseBuilder street(String street);

    Warehouse build();


}

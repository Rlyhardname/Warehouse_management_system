package com.example.warehouses.Repository;

import com.example.warehouses.Model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
}

package com.example.warehouses.repository;

import com.example.warehouses.model.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    //@Query("Select s FROM Warehouse s WHERE s.warehouseName =?1")
    Optional<Warehouse> findByName(String name);

    @Query("Select s FROM Warehouse s WHERE s.owner.Id =?1")
    List<Warehouse> findWarehousesByIdOwner(Long id);

    @Query("Select s FROM Warehouse s WHERE s.owner.Id =?1 AND s.id =?2")
    Optional<Warehouse> findById_OwnerIdAndId_WarehouseId(Long ownerId, Long warehouseId);

    @Query("Select s FROM Warehouse s WHERE s.rented =?1")
    Optional<List<Warehouse>> findByRentStatus(boolean rented);
}

package com.example.warehouses.repository;

import com.example.warehouses.model.warehouse.WarehouseAsignedToAgentPK;
import com.example.warehouses.model.warehouse.WarehouseAssignedToAgent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseAssignedToAgentRepository extends JpaRepository<WarehouseAssignedToAgent, WarehouseAsignedToAgentPK> {

//    @Query("Select s FROM WarehouseAssignedToAgent s WHERE s.owner.Id =?1 AND s.id.warehouseId =?2")
//    Optional<List<WarehouseAssignedToAgent>> findWarehousesByOwnerIdAndWarehouseId(Long ownerId, Long warehouseId);
@Query("Select s FROM WarehouseAssignedToAgent s WHERE s.id.warehouseId =?1")
Optional<List<WarehouseAssignedToAgent>> findByWarehouseId(Long warehouseId);

    @Query("Select s FROM WarehouseAssignedToAgent s WHERE s.id.agentId =?1 AND s.id.warehouseId =?2")
    Optional<WarehouseAssignedToAgent> findByAgentIdAndWarehouseId(Long agentId, Long warehouseId);

    @Transactional
    @Modifying
    @Query("UPDATE WarehouseAssignedToAgent s SET s.relationshipStatus=?1 WHERE s.id.agentId =?2 AND s.id.warehouseId=?3")
    void updateStatus(String status, Long agentId, Long warehouseId);

}

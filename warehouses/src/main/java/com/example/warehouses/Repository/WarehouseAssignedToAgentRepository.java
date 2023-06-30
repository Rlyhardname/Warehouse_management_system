package com.example.warehouses.Repository;

import com.example.warehouses.Model.warehouse.WarehouseAsignedToAgentPK;
import com.example.warehouses.Model.warehouse.WarehouseAssignedToAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseAssignedToAgentRepository extends JpaRepository<WarehouseAssignedToAgent, WarehouseAsignedToAgentPK> {

    @Query("Select s FROM WarehouseAssignedToAgent s WHERE s.owner.Id =?1 AND s.id.warehouseId =?2")
    Optional<List<WarehouseAssignedToAgent>> findWarehousesByOwnerIdAndWarehouseId(Long ownerId, Long warehouseId);

    @Query("Select s FROM WarehouseAssignedToAgent s WHERE s.id.agentId =?1 AND s.id.warehouseId =?2")
    Optional<WarehouseAssignedToAgent> findByAgentIdAndWarehouseId(Long agentId, Long warehouseId);

}

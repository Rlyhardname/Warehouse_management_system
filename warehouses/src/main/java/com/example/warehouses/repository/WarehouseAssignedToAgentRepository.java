package com.example.warehouses.repository;

import com.example.warehouses.model.warehouse.WarehouseAssignedToAgentPK;
import com.example.warehouses.model.warehouse.WarehouseAssignedToAgent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseAssignedToAgentRepository extends JpaRepository<WarehouseAssignedToAgent, WarehouseAssignedToAgentPK> {

    //@Query("Select s FROM WarehouseAssignedToAgent s WHERE s.id.warehouseId =?1")
    List<WarehouseAssignedToAgent> findAllByIdWarehouseId(@Param("warehouseId") Long warehouseId);

    //@Query("Select s FROM WarehouseAssignedToAgent s WHERE s.id.agentId =?1 AND s.id.warehouseId =?2")
    Optional<WarehouseAssignedToAgent> findById_AgentIdAndId_WarehouseId(@Param("agentId") Long agentId, @Param ("warehouseId") Long warehouseId);

    @Transactional
    @Modifying
    @Query("UPDATE WarehouseAssignedToAgent wa SET wa.relationshipStatus = :relationshipStatus WHERE wa.id.agentId = :agentId AND wa.id.warehouseId = :warehouseId")
    void updateStatus(@Param("relationshipStatus") String relationshipStatus,@Param("agentId") Long agentId,@Param("warehouseId") Long warehouseId);

}

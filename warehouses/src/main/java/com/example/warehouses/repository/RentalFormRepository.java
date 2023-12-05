package com.example.warehouses.repository;

import com.example.warehouses.model.warehouse.RentalForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalFormRepository extends JpaRepository<RentalForm, Long> {

    @Query("SELECT ar FROM RentalForm ar WHERE ar.agent.Id = :agentId AND ar.startDate <= :startDate AND ar.endDate >= :endDate")
    Optional<List<RentalForm>> findRentFormsByAgentIdAndStartDateEndDate(@Param("agentId") Long agentId,
                                                                         @Param("startDate") LocalDate startDate,
                                                                         @Param("endDate") LocalDate endDate);

}

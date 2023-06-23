package com.example.warehouses.Repository;

import com.example.warehouses.Model.AgentRatings;
import com.example.warehouses.Model.AgentRatingsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<AgentRatings, AgentRatingsPK> {

    @Query("SELECT ar FROM AgentRatings ar WHERE ar.id.agentID = :agentId")
    List<AgentRatings> findByAgentId(@Param("agentId") Long agentId);

    @Query("SELECT ar FROM AgentRatings ar WHERE ar.id.ownerID = :ownerID")
    List<AgentRatings> findByOwnerId(@Param("ownerID") Long ownerID);
}

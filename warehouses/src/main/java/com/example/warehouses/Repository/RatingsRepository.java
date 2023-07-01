package com.example.warehouses.Repository;

import com.example.warehouses.Model.AgentRatings;
import com.example.warehouses.Model.AgentRatingsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingsRepository extends JpaRepository<AgentRatings, AgentRatingsPK> {

    @Query("SELECT ar FROM AgentRatings ar WHERE ar.id.agentID = :agentId")
    Optional<List<AgentRatings>> findAllByAgentId(@Param("agentId") Long agentId);

    @Query("SELECT ar FROM AgentRatings ar WHERE ar.id.ownerID = :ownerID")
    Optional<List<AgentRatings>> findByOwnerId(@Param("ownerID") Long ownerID);
}

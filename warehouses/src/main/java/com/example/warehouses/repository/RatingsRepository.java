package com.example.warehouses.repository;

import com.example.warehouses.model.AgentRatings;
import com.example.warehouses.model.AgentRatingsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingsRepository extends JpaRepository<AgentRatings, AgentRatingsPK> {

    //@Query("SELECT ar FROM AgentRatings ar WHERE ar.id.agentID = :agentId")
    //(@Param("agentId") Long agentId);
    List<AgentRatings> findAllByIdOwnerID(Long ownerID);

    // @Query("SELECT ar FROM AgentRatings ar WHERE ar.id.ownerID = :ownerID")
    List<AgentRatings> findAllByIdAgentID(Long agentID);

    Optional<AgentRatings> findByIdAgentID(Long agentID);

    boolean existsByIdAgentID(Long AgentID);


}

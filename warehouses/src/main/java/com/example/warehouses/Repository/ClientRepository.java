package com.example.warehouses.Repository;

import com.example.warehouses.Model.User.Agent;
import com.example.warehouses.Model.User.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("Select s FROM Client s WHERE s.email =?1")
    Optional<Client> findClientByEmail(String email);

    @Query("Select s FROM Agent s WHERE s.accountType=:type")
    Optional<List<Agent>> selectAllAgents(String type);

    @Query("Select s FROM Client s WHERE s.email=?1") // duplicating method, remove 1 instance
    Optional<Client> findByUsername(String email);


//    @Transactional
//    @Modifying
//    @Query("UPDATE Owner o set o.warehousesOwned = ?1 where o.email = ?2")
//    void updateOwnerWarehouses(Set<Warehouse> wareHousesOwned,String email);
}


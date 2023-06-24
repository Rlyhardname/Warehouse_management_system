package com.example.warehouses.Repository;

import com.example.warehouses.Interfaces.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("Select s FROM Client s WHERE s.email =?1")
    Optional<Client> findClientByEmail(String email);


//    @Transactional
//    @Modifying
//    @Query("UPDATE Owner o set o.warehousesOwned = ?1 where o.email = ?2")
//    void updateOwnerWarehouses(Set<Warehouse> wareHousesOwned,String email);
}


package com.example.warehouses.Repository;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.Owner;
import com.example.warehouses.Model.Warehouse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    @Query("Select s FROM Client s WHERE s.email =?1")
    Optional<Client> findClientByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Owner o set o.warehousesOwned = ?1 where o.email = ?2")
    void updateOwnerWarehouses(Set<Warehouse> wareHousesOwned,String email);
}

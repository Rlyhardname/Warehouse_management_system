package com.example.warehouses.Repository;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Model.Owner;
import com.example.warehouses.Model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AdminRepository extends JpaRepository<Administrator,Long> {

    @Query("Select s FROM Administrator s WHERE s.email =?1")
    Optional<Administrator> findAdminByEmail(String email);



    // Optional<Administrator>
}

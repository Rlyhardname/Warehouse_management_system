package com.example.warehouses.Repository;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Model.MasterAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Administrator,Long> {

    @Query("Select s FROM Administrator s WHERE s.email =?1")
    Administrator findAdminByEmail(String email);
    // Optional<Administrator>
}

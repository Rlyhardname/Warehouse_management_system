package com.example.warehouses.Repository;

import com.example.warehouses.Interfaces.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Administrator,Long> {


}

package com.example.warehouses.Repository;

import com.example.warehouses.Interfaces.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}

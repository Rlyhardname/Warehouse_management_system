package com.example.warehouses.Repository;

import com.example.warehouses.Model.User.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // @Query("Select s FROM Client s WHERE s.email =?1")
    Optional<Client> findByEmail(String email);

    Client readByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    //@Query("Select s FROM Agent s WHERE s.accountType=:type")

    boolean existsBydType(String type);

    List<Client> findBydType(String dType);


}


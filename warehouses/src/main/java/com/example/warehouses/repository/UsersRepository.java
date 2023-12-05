package com.example.warehouses.repository;

import com.example.warehouses.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    // @Query("Select s FROM Client s WHERE s.email =?1")
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    //@Query("Select s FROM Agent s WHERE s.accountType=:type")

    boolean existsBydType(String type);

    List<User> findBydType(String dType);


}


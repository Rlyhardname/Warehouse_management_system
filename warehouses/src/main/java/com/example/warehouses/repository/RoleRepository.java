package com.example.warehouses.repository;

import com.example.warehouses.model.user.User;
import com.example.warehouses.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("Select s FROM Role s WHERE s.roleName=?1")
    Optional<User> findByRole(String role);
}

package com.example.warehouses.Repository;

import com.example.warehouses.Model.User.User;
import com.example.warehouses.Model.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("Select s FROM Role s WHERE s.roleName=?1")
    Optional<User> findByRole(String role);
}

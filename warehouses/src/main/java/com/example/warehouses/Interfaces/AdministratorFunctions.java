package com.example.warehouses.Interfaces;

import com.example.warehouses.Model.User.Client;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public interface AdministratorFunctions {
    public Optional<Administrator> isLoginAdmin(String email, String password, HttpServletResponse response) throws IOException;
    public Client createClient(String email,
                                         String password,
                                         String firstName,
                                         String lastName,
                                         String clientType,
                                         HttpServletResponse response);
}

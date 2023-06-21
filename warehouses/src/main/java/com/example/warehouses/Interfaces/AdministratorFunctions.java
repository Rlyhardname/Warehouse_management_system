package com.example.warehouses.Interfaces;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public interface AdministratorFunctions {
    public Optional<Administrator> isLoginClient(String email, String password, HttpServletResponse response) throws IOException;
    public Optional<Client> createClient(String email,
                                         String password,
                                         String firstName,
                                         String lastName,
                                         String clientType,
                                         HttpServletResponse response);
}

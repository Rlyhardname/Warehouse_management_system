package com.example.warehouses.interfaces;

import com.example.warehouses.model.user.UserImpl;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public interface AdministratorFunctions {
    public Optional<Administrator> isLoginAdmin(String email, String password, HttpServletResponse response) throws IOException;
    public UserImpl createClient(String email,
                                 String password,
                                 String firstName,
                                 String lastName,
                                 String clientType,
                                 HttpServletResponse response);
}

package com.example.warehouses.Interfaces;

import jakarta.servlet.http.HttpServletResponse;

public interface AdministratorFunctions {
    public void loginClient(String email, String password, HttpServletResponse response);
}

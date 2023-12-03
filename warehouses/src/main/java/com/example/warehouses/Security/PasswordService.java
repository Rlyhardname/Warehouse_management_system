package com.example.warehouses.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String password) {
        if (password != null && !password.isEmpty()) {
            return passwordEncoder.encode(password);
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

}
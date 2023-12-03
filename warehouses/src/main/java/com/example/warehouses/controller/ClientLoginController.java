package com.example.warehouses.controller;

import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Services.CustomClientService;
import com.example.warehouses.Services.ClientService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Validated
@RequestMapping(path = "/login")
public class ClientLoginController {

    private final CustomClientService clientService;
    private final ClientService globalService;

    @Autowired
    public ClientLoginController(CustomClientService clientService,
                                 ClientService globalService) {
        this.clientService = clientService;
        this.globalService = globalService;
    }

    // For removal, implemented before Spring security implementation.
    @SneakyThrows
    @PostMapping
    public Optional<Client> isLoginClient(@RequestParam String email,
                                          @RequestParam String password,
                                          HttpServletResponse response) {

        Optional<Client> adminOpt = null;
        adminOpt = clientService.isLoginClient(email, password, response);
        response.sendRedirect("login");

        return adminOpt;
    }


}

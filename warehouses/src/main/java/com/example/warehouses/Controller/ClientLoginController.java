package com.example.warehouses.Controller;

import com.example.warehouses.Model.User.Client;
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

    private final ClientService clientService;

    @Autowired
    public ClientLoginController(ClientService clientService) {
        this.clientService = clientService;
    }

    // For removal, implmented before Spring security implementation.
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

    @SneakyThrows
    @PostMapping("createOwner")
    public Optional<Client> registerClient(@RequestParam String email,
                                           @RequestParam String password,
                                           @RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String clientType,
                                           HttpServletResponse response) {

        Optional<Client> clientOpt = clientService.register(email, password, firstName, lastName, clientType, response);
        response.sendRedirect("http://localhost:8080/MainPage.html");

        return clientOpt;
    }

}

package com.example.warehouses.controller.user;

import com.example.warehouses.Exception.Client.ClientAlreadyRegisteredException;
import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Services.ClientService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/main")
public class UsersController {
    private final ClientService clientService;

    @Autowired
    public UsersController(ClientService clientService) {
        this.clientService = clientService;
    }

    @SneakyThrows
    @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )//"application/x-www-form-urlencoded")
    public ResponseEntity<String> registerClient(@Valid @ModelAttribute Client client,
                                                 HttpServletResponse response) {
        if (!clientService.isUsernameTaken(client.getEmail())) {
            clientService.register(client.getEmail(), client.getPassword(), client.getFirstName(), client.getDType(), client.getLastName(), response); //,
            return new ResponseEntity<>("User Successfully registered!", HttpStatus.ACCEPTED);
        } else {
            throw new ClientAlreadyRegisteredException();
        }
    }

    // TODO Login/Configure the Spring Security
}

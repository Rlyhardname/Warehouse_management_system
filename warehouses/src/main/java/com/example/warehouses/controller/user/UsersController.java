package com.example.warehouses.controller.user;

import com.example.warehouses.exception.Client.ClientAlreadyRegisteredException;
import com.example.warehouses.model.user.UserImpl;
import com.example.warehouses.services.UsersService;
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
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @SneakyThrows
    @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
    )//"application/x-www-form-urlencoded")
    public ResponseEntity<String> registerClient(@Valid @ModelAttribute UserImpl userImpl,
                                                 HttpServletResponse response) {
        if (!usersService.isUsernameTaken(userImpl.getEmail())) {
            usersService.register(userImpl.getEmail(), userImpl.getPassword(), userImpl.getFirstName(), userImpl.getDType(), userImpl.getLastName(), response); //,
            return new ResponseEntity<>("User Successfully registered!", HttpStatus.ACCEPTED);
        } else {
            throw new ClientAlreadyRegisteredException();
        }
    }

    // TODO Login/Configure the Spring Security
}

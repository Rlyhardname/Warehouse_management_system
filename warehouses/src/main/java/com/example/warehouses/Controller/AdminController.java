package com.example.warehouses.Controller;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.AdministratorFunctions;
import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Services.AdminService;
import com.example.warehouses.Services.GlobalService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
@RequestMapping(path = "hidden/admin/")
public class AdminController implements AdministratorFunctions {
    private final AdminService adminService;
    private final GlobalService globalService;


    @Autowired
    public AdminController(AdminService adminService,
                           GlobalService globalService) {
        this.adminService = adminService;
        this.globalService = globalService;
    }

    @SneakyThrows
    @PostMapping
    @Override
    public Optional<Administrator> isLoginAdmin(@RequestParam String email,
                                                @RequestParam String password,
                                                HttpServletResponse response) {

        Optional<Administrator> adminOpt = null;
        adminOpt = adminService.isLoginAdmin(email, password, response);
        response.sendRedirect("login");

        return adminOpt;
    }

    @SneakyThrows
    @PostMapping("createClient")
    @Override
    public Optional<Client> createClient(@RequestParam String email,
                                         @RequestParam String password,
                                         @RequestParam String firstName,
                                         @RequestParam String lastName,
                                         @RequestParam String clientType,
                                         HttpServletResponse response) {

        Optional<Client> clientOpt = adminService.createClient(email, password, firstName, lastName, clientType, response);
        response.sendRedirect("http://localhost:8080/MainPage.html");

        return clientOpt;
    }

    @PostMapping("createUser")
    public String createUser(@RequestParam Long adminId,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String type
    ) {
        String attempt = "User is taken!";
        if(!globalService.isUsernameTaken(email)){
            attempt = adminService.createUser(adminId,
                    email,
                    password,
                    firstName,
                    lastName,
                    type);
        }

        return attempt;
    }


}

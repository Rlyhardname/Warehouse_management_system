package com.example.warehouses.Controller;

import com.example.warehouses.Exception.UserNotExististingException;
import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.AdministratorFunctions;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Services.AdminService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(path ="hidden/admin/")
public class AdminLoginController implements AdministratorFunctions {

    private final AdminService adminService;
    @Autowired
    public AdminLoginController(AdminService adminService) {this.adminService =adminService;}

    @PostMapping
    @Override
    public Optional<Administrator> isLoginClient(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {

        Optional<Administrator> adminOpt = null;
        try {
            adminOpt = adminService.isLoginClient(email, password, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
              Administrator admin =  adminOpt.orElseThrow(()-> new UserNotExististingException(
                ));

                //  response.getWriter();
                // adminService.getAdminRepository().findAdminByEmail();
                response.sendRedirect("login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        return adminOpt;
    }

    @SneakyThrows
    @PostMapping("createOwner")
    @Override
    public Optional<Client> createClient(@RequestParam String email,
                                         @RequestParam String password,
                                         @RequestParam String firstName,
                                         @RequestParam String lastName,
                                         @RequestParam String clientType,
                                         HttpServletResponse response) {


        Optional<Client> clientOpt = adminService.createClient(email,password,firstName,lastName,clientType, response);
        response.sendRedirect("http://localhost:8080/MainPage.html");

        return clientOpt;
    }

    @GetMapping("")
    public void potatoRedirect(HttpServletResponse response){
        try {

            response.sendRedirect("http://localhost:8080/adminLogin.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}




//    @GetMapping
//    public void testDB(){
//        adminService.testService();
//    }
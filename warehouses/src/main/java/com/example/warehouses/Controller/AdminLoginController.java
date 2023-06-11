package com.example.warehouses.Controller;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.AdministratorFunctions;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Repository.AdminRepository;
import com.example.warehouses.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="hidden/admin/login")
public class AdminLoginController implements AdministratorFunctions {

    private final AdminService adminService;
    @Autowired
    public AdminLoginController(AdminService adminService) {this.adminService =adminService;}

    @PostMapping
    @Override
    public void createClient(@RequestBody Client client) {

        AdminRepository repo = adminService.getAdminRepository();

    }

    @GetMapping
    public void testDB(){
        adminService.testService();
    }
}

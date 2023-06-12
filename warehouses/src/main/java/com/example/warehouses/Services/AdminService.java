package com.example.warehouses.Services;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.AdministratorFunctions;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.MasterAdmin;
import com.example.warehouses.Model.TestAdmin;
import com.example.warehouses.Repository.AdminRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.http.HttpResponse;

@Getter
@Service
public class AdminService implements AdministratorFunctions {

    private final AdminRepository adminRepository;
    @Autowired
    public AdminService(AdminRepository adminRepository){this.adminRepository = adminRepository;}

    public void testService(){
        adminRepository.save(new MasterAdmin());
        adminRepository.save(new TestAdmin());
       Administrator admin = adminRepository.findById(1L).orElseThrow(()->new IllegalStateException(
                "HAHAHA"
        ));
    }


    @Override
    public void loginClient(String email, String password, HttpServletResponse response) {

        Administrator admin = adminRepository.findAdminByEmail(email);


        if(admin != null){
            if(admin.getPassword().equals(password)){
                login(email,password);
            }
        } else{
            try {
                response.sendRedirect("/adminLogin.html");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @GetMapping("/adminLogin/panel/admin")
    private void login(String email, String password) {



    }
}

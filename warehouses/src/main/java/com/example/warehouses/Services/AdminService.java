package com.example.warehouses.Services;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.AdministratorFunctions;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.Agent;
import com.example.warehouses.Model.MasterAdmin;
import com.example.warehouses.Model.TestAdmin;
import com.example.warehouses.Repository.AdminRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
       System.out.println(admin.getAccountType());
    }


    @Override
    public void createClient(Client client) {

    }
}

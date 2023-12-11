package com.example.warehouses.services;

import com.example.warehouses.model.user.Agent;
import com.example.warehouses.model.user.UserFactory;
import com.example.warehouses.model.user.UserImpl;
import com.example.warehouses.model.user.Owner;
import com.example.warehouses.repository.AdminRepository;
import com.example.warehouses.repository.UsersRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final AdminRepository adminRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(AdminRepository adminRepository, UsersRepository usersRepository) {
        this.adminRepository = adminRepository;
        this.usersRepository = usersRepository;

    }

    public UserImpl register(String email,
                             String password,
                             String firstName,
                             String lastName,
                             String clientType,
                             HttpServletResponse response) {

        UserImpl userImpl = UserFactory.instanceOf(clientType,email,password,firstName,lastName);
            usersRepository.save(userImpl);
        return userImpl;
    }


    public boolean isUsernameTaken(String username){
        if(usersRepository.findByEmail(username).isPresent()){
            return true;
        }if(adminRepository.findByEmail(username).isPresent()){
            return true;
        }
        return false;
    }


}

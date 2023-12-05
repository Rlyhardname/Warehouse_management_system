package com.example.warehouses.services;

import com.example.warehouses.model.user.Agent;
import com.example.warehouses.model.user.User;
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

    public User register(String email,
                         String password,
                         String firstName,
                         String lastName,
                         String clientType,
                         HttpServletResponse response) {

        User user = null;
        if (clientType.equals("owner")) {
            user = new Owner();
            ((Owner) user).init(email, password, firstName, lastName);
        } else if (clientType.equals("agent")) {
            user = new Agent();
            ((Agent) user).init(email, password, firstName, lastName);
        }
            usersRepository.save(user);
        return user;
    }


    public boolean isUsernameTaken(String username){
        if(usersRepository.findByEmail(username).isPresent()){
            return true;
        }if(adminRepository.findAdminByEmail(username).isPresent()){
            return true;
        }
        return false;
    }


}

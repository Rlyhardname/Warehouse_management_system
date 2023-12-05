package com.example.warehouses.services;

import com.example.warehouses.exception.Client.ClientAlreadyRegisteredException;
import com.example.warehouses.exception.Client.UserNotExististingException;
import com.example.warehouses.exception.Login.WrongPasswordException;
import com.example.warehouses.exception.admin.AdminDoesntExistException;
import com.example.warehouses.interfaces.Administrator;
import com.example.warehouses.interfaces.AdministratorFunctions;
import com.example.warehouses.model.user.User;
import com.example.warehouses.model.user.MasterAdmin;
import com.example.warehouses.repository.AdminRepository;
import com.example.warehouses.repository.UsersRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Getter
@Service
public class AdminService implements AdministratorFunctions {

    private final AdminRepository adminRepository;
    private final UsersRepository usersRepository;
    private final UsersService globalService;

    @Autowired
    public AdminService(AdminRepository adminRepository, UsersRepository usersRepository, UsersService globalService) {
        this.adminRepository = adminRepository;
        this.usersRepository = usersRepository;
        this.globalService = globalService;

    }

    @Override
    public Optional<Administrator> isLoginAdmin(String email, String password, HttpServletResponse response) throws IOException {

        Optional<Administrator> adminOpt = Optional.of(adminRepository.findByEmail(email).orElseThrow(
                () -> new UserNotExististingException()
        ));
        System.out.println(adminOpt.get().getEmail() + " " + adminOpt.get().getPassword());
        if (!adminOpt.get().getPassword().equals(password)) {
            throw new WrongPasswordException();
        }
        return adminOpt;
    }

    @Override
    public User createClient(String email,
                             String password,
                             String firstName,
                             String lastName,
                             String clientType,
                             HttpServletResponse response) {

        return globalService.register(email,
                password,
                firstName,
                lastName,
                clientType,
                response);
    }

    public String createUser(Long adminId,
                             String email,
                             String password,
                             String firstName,
                             String lastName,
                             String type) {

        MasterAdmin admin = (MasterAdmin) adminRepository.findById(adminId).orElseThrow(
                () -> new AdminDoesntExistException()
        );
        User user = admin.createUser(email,
                password,
                firstName,
                lastName,
                type);

        String message = "Successfully created an ";
        if (type.equals("owner")) message += "owner";
        if (type.equals("agent")) message += "agent";

        if (usersRepository.findById(user.getId()).isPresent() == false) {
            usersRepository.save(user);
        } else {
            throw new ClientAlreadyRegisteredException();
        }
        return message;

    }

}



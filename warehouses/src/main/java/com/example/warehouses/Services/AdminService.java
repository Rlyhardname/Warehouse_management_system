package com.example.warehouses.Services;

import com.example.warehouses.Exception.Client.ClientAlreadyRegisteredException;
import com.example.warehouses.Exception.Client.UserNotExististingException;
import com.example.warehouses.Exception.Login.WrongPasswordException;
import com.example.warehouses.Exception.admin.AdminDoesntExistException;
import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.AdministratorFunctions;
import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Model.User.MasterAdmin;
import com.example.warehouses.Repository.AdminRepository;
import com.example.warehouses.Repository.ClientRepository;
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
    private final ClientRepository clientRepository;
    private final GlobalService globalService;

    @Autowired
    public AdminService(AdminRepository adminRepository, ClientRepository clientRepository, GlobalService globalService) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.globalService = globalService;

    }

    @Override
    public Optional<Administrator> isLoginAdmin(String email, String password, HttpServletResponse response) throws IOException {

        Optional<Administrator> adminOpt = Optional.of(adminRepository.findAdminByEmail(email).orElseThrow(
                () -> new UserNotExististingException()
        ));
        System.out.println(adminOpt.get().getEmail() + " " + adminOpt.get().getPassword());
        if (!adminOpt.get().getPassword().equals(password)) {
            throw new WrongPasswordException();
        }
        return adminOpt;
    }

    @Override
    public Optional<Client> createClient(String email,
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
        Client client = admin.createUser(email,
                password,
                firstName,
                lastName,
                type);

        String message = "Successfully created an ";
        if (type.equals("owner")) message += "owner";
        if (type.equals("agent")) message += "agent";

        if (clientRepository.findById(client.getId()).isPresent() == false) {
            clientRepository.save(client);
        } else {
            throw new ClientAlreadyRegisteredException();
        }
        return message;

    }

}



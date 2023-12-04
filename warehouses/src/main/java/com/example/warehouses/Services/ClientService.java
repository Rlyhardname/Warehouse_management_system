package com.example.warehouses.Services;

import com.example.warehouses.Model.User.Agent;
import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Model.User.Owner;
import com.example.warehouses.Repository.AdminRepository;
import com.example.warehouses.Repository.ClientRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(AdminRepository adminRepository, ClientRepository clientRepository) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;

    }

    public Client register(String email,
                                     String password,
                                     String firstName,
                                     String lastName,
                                     String clientType,
                                     HttpServletResponse response) {

        Client client = null;
        if (clientType.equals("owner")) {
            client = new Owner();
            ((Owner) client).init(email, password, firstName, lastName);
        } else if (clientType.equals("agent")) {
            client = new Agent();
            ((Agent) client).init(email, password, firstName, lastName);
        }
            clientRepository.save(client);
        return client;
    }


    public boolean isUsernameTaken(String username){
        if(clientRepository.findByEmail(username).isPresent()){
            return true;
        }if(adminRepository.findAdminByEmail(username).isPresent()){
            return true;
        }
        return false;
    }


}

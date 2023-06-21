package com.example.warehouses.Services;

import com.example.warehouses.Exception.ClientAlreadyRegisteredException;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.Agent;
import com.example.warehouses.Model.Owner;
import com.example.warehouses.Repository.AdminRepository;
import com.example.warehouses.Repository.ClientRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GlobalService {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public GlobalService(AdminRepository adminRepository, ClientRepository clientRepository) {
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;

    }

    public Optional<Client> register(String email,
                                     String password,
                                     String firstName,
                                     String lastName,
                                     String clientType,
                                     HttpServletResponse response) {

        Client client = null;
        if (clientType.equals("Owner")) {
            client = new Owner();
            ((Owner) client).init(email, password, firstName, lastName);
        } else if (clientType.equals("Agent")) {
            client = new Agent();
            ((Agent) client).init(email, password, firstName, lastName);
        }


        Optional<Client> clientOpt = clientRepository.findClientByEmail(email);
        if (clientOpt.isPresent() == false) {
            clientRepository.save(client);
        } else if (clientOpt != null) {
            throw new ClientAlreadyRegisteredException();
        }

        return clientOpt;
    }

}

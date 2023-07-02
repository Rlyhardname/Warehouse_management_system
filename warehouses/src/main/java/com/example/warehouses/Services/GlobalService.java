package com.example.warehouses.Services;

import com.example.warehouses.Configurations.Enum.WarehouseCategory;
import com.example.warehouses.Exception.Client.ClientAlreadyRegisteredException;
import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Model.User.Agent;
import com.example.warehouses.Model.User.Owner;
import com.example.warehouses.Repository.AdminRepository;
import com.example.warehouses.Repository.ClientRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    public WarehouseCategory warehouseCategory(String category){
        switch (category.toLowerCase()){
            case "garage": return WarehouseCategory.GARAGE;
            case "SMALL": return WarehouseCategory.SMALL;
            case "MEDIUM": return WarehouseCategory.MEDIUM;
            case "LARGE": return WarehouseCategory.LARGE;
            case "INDUSTRIAL":  return WarehouseCategory.INDUSTRIAL;
            default: return WarehouseCategory.EMPTY;
        }
    }

    public boolean isUsernameTaken(String username){
        if(clientRepository.findClientByEmail(username).isPresent()){
            return true;
        }if(adminRepository.findAdminByEmail(username).isPresent()){
            return true;
        }
        return false;
    }


}

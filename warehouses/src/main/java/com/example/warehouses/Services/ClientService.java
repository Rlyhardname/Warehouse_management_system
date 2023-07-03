package com.example.warehouses.Services;

import com.example.warehouses.Exception.Client.UserNotExististingException;
import com.example.warehouses.Exception.Login.WrongPasswordException;
import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Repository.ClientRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final GlobalService globalService;

    @Autowired
    public ClientService(ClientRepository clientRepository, GlobalService globalService) {
        this.clientRepository = clientRepository;
        this.globalService = globalService;
    }

    public Optional<Client> isLoginClient(String email, String password, HttpServletResponse response) throws IOException {

        Optional<Client> adminOpt = Optional.of(clientRepository.findClientByEmail(email).orElseThrow(
                () -> new UserNotExististingException()
        ));
        System.out.println(adminOpt.get().getEmail() + " " + adminOpt.get().getPassword());
        if (!adminOpt.get().getPassword().equals(password)) {
            throw new WrongPasswordException();
        }
        return adminOpt;
    }


}

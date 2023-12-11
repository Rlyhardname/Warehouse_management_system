package com.example.warehouses.services;

import com.example.warehouses.exception.Client.UserNotExististingException;
import com.example.warehouses.exception.Login.WrongPasswordException;
import com.example.warehouses.model.user.UserImpl;
import com.example.warehouses.repository.UsersRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class CustomClientService {

    private final UsersRepository usersRepository;
    private final UsersService globalService;

    @Autowired
    public CustomClientService(UsersRepository usersRepository, UsersService globalService) {
        this.usersRepository = usersRepository;
        this.globalService = globalService;
    }

    public Optional<UserImpl> isLoginClient(String email, String password, HttpServletResponse response) throws IOException {

        Optional<UserImpl> adminOpt = Optional.of(usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotExististingException()
        ));
        System.out.println(adminOpt.get().getEmail() + " " + adminOpt.get().getPassword());
        if (!adminOpt.get().getPassword().equals(password)) {
            throw new WrongPasswordException();
        }
        return adminOpt;
    }


}

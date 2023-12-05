package com.example.warehouses.Services;

import com.example.warehouses.Exception.Client.UserNotExististingException;
import com.example.warehouses.Exception.Login.WrongPasswordException;
import com.example.warehouses.Model.User.User;
import com.example.warehouses.Repository.UsersRepository;
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

    public Optional<User> isLoginClient(String email, String password, HttpServletResponse response) throws IOException {

        Optional<User> adminOpt = Optional.of(usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotExististingException()
        ));
        System.out.println(adminOpt.get().getEmail() + " " + adminOpt.get().getPassword());
        if (!adminOpt.get().getPassword().equals(password)) {
            throw new WrongPasswordException();
        }
        return adminOpt;
    }


}

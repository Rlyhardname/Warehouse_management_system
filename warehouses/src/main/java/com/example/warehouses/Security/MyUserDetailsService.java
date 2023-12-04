package com.example.warehouses.Security;

import com.example.warehouses.Configurations.Enum.Role;
import com.example.warehouses.Exception.Client.UserNotExististingException;
import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Repository.AdminRepository;
import com.example.warehouses.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private ClientRepository clientRepository;
    private AdminRepository adminRepository;

    @Autowired
    public MyUserDetailsService(ClientRepository clientRepository,
                                AdminRepository adminRepository) {
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public User loadUserByUsername(String email1) {
        String email = null;
        String password = null;
        String ROLE = null;
        Optional<Administrator> admin;
        Optional<Client> client = clientRepository.findByEmail(email1);

        if (client.isPresent()) {
            email = client.get().getEmail();
            password = client.get().getPassword();
            if (client.get().getDType().equals("owner")) {
                ROLE = Role.OWNER.name();
            } else {
                ROLE = Role.AGENT.name();
            }
        } else {
            admin = adminRepository.findAdminByEmail(email1);
            if (admin.isPresent() == true) {
                email = admin.get().getEmail();
                password = admin.get().getPassword();
                ROLE = Role.ADMIN.name();
            } else{
                throw new UserNotExististingException();
            }
        }

        SecurityProperties.User user = new SecurityProperties.User();

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());

        User user1 = new User(email, password, ROLE, authorities);

        return user1;

    }
}

package com.example.warehouses.Services;

import com.example.warehouses.Model.User.Client;
import com.example.warehouses.Model.User.User;
import com.example.warehouses.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private ClientRepository clientRepository;

    @Autowired
    public MyUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public User loadUserByUsername(String username) {
        Client client = clientRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "User not found with username or email" + username));
        ;

        SecurityProperties.User user = new SecurityProperties.User();
        user.setName(client.getEmail());
        user.setPassword((client.getPassword()));

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());
        User user1 = new User(client.getEmail(), client.getPassword(), authorities);

        return user1;

    }
}

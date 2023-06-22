package com.example.warehouses.Configurations;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.MasterAdmin;
import com.example.warehouses.Model.Owner;
import com.example.warehouses.Model.TestAdmin;
import com.example.warehouses.Repository.AdminRepository;
import com.example.warehouses.Repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UsersConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(AdminRepository repository, ClientRepository clientRepository){
        return args -> {
            Administrator admin1 = new MasterAdmin();
            ((MasterAdmin) admin1).init( "admin1@gmail.com",
                    "Monkeyfingers",
                    "Ivan",
                    "Dimitrov");

            Administrator admin2 = new TestAdmin();
            ((TestAdmin) admin2).init( "admin2@gmail.com",
                    "LolipopsYeee",
                    "Jack",
                    "Daniels");

           Client client = new Owner();
            ((Owner) client).init(
                    "client1@gmail.com",
                    "hello",
                    "Black",
                    "Swan"
            );




            repository.saveAll(
                    List.of(admin1,admin2)

            );

            clientRepository.save(client);


            System.out.println("Test Admins Added");

        };
    }
}

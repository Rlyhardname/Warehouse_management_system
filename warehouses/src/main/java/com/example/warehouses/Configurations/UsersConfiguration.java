package com.example.warehouses.Configurations;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.*;
import com.example.warehouses.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Configuration
public class UsersConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(AdminRepository repository,
                                        ClientRepository clientRepository,
                                        RatingsRepository ratingsRepository,
                                        RentalFormRepository rentalFormRepository,
                                        WarehouseRepository warehouseRepository,
                                        AddressRepository addressRepository){
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

            Client client1 = new Owner();
            ((Owner) client).init(
                    "owner2@gmail.com",
                    "hello",
                    "White",
                    "Truffle"
            );


            Agent agent1 = new Agent();
            agent1.init(  "agent1@gmail.com",
                    "hello",
                    "Orange",
                    "Juice");
            Agent agent2 = new Agent();
            agent2.init(  "agent2@gmail.com",
                    "hello",
                    "Yellow",
                    "Lamborghini");


            repository.saveAll(
                    List.of(admin1,admin2)

            );

            clientRepository.saveAll(
                    List.of(client,client1,agent1,agent2)
            );

            Address address1 = new Address();
            address1.init("Varna","Varna","Dobrovnik 13, vh A");
            Address address2 = new Address();
            address2.init("Targovishte","Opaka","Tsar osvoboditel 2");

            addressRepository.saveAll(
                    List.of(address1,address2)
            );

            Warehouse warehouse1 = new Warehouse();
            warehouse1.init((Owner)client1,address1,"EcontVarnaMain", "1000","22","25","retail",
                    "commercial","no");

            Warehouse warehouse2 = new Warehouse();
            warehouse2.init((Owner)client1,address2,"SkladZaDrehi", "1000","17","33","clothes",
                    "garage","yes");

            warehouseRepository.save(warehouse1);
            warehouseRepository.save(warehouse2);




            AgentRatingsPK pk1 = new AgentRatingsPK(client.getId(), agent1.getId());
            AgentRatingsPK pk11 = new AgentRatingsPK(client.getId(),agent2.getId());
            AgentRatingsPK pk2 = new AgentRatingsPK(client1.getId(),agent1.getId());
            AgentRatingsPK pk22 = new AgentRatingsPK(client1.getId(),agent2.getId());
            AgentRatings rating1 = new AgentRatings(pk1, 5);
            AgentRatings rating2 = new AgentRatings(pk11, 2);
            AgentRatings rating3 = new AgentRatings(pk2, 4);
            AgentRatings rating4 = new AgentRatings(pk22, 1);
            //AgentRatings rating1 = new AgentRatings(1L,1L, 5);

            ratingsRepository.saveAll(
                    List.of(rating1,rating2,rating3
                    ,rating4)
            );
            Optional<List<AgentRatings>> ratingsBasedOnOwnerID = ratingsRepository.findByOwnerId(1L);
            Optional<List<AgentRatings>> ratingsBasedOnAgentID = ratingsRepository.findByAgentId(3L);
//
            for (AgentRatings item: ratingsBasedOnOwnerID.get()
              ) {
                System.out.println("Owner ratings with id's of 1L value " + item);
            }

            for (AgentRatings item: ratingsBasedOnAgentID.get()
            ) {
                System.out.println("Agent ratings with id's of 3L value " + item);
            }

            LocalDate start = LocalDate.of(2021,4,24);
            LocalDate end = LocalDate.of(2023,6,26);
            LocalDate start1 = LocalDate.of(2020,1,24);
            LocalDate end1 = LocalDate.of(2024,1,26);
            RentalForm rentalForm1 = new RentalForm(agent2,agent1,warehouse1,start,end,500.50);
            RentalForm rentalForm2 = new RentalForm(agent2,client1,warehouse1,start1,end1,444);
            rentalFormRepository.save(rentalForm1);
            rentalFormRepository.save(rentalForm2);
            warehouseRepository.save(warehouse1);
            List<Optional<Warehouse>> warehouses = warehouseRepository.findByRentStatus(true);
            for (Optional<Warehouse> item : warehouses
                 ) {
                System.out.print(item);
            }


            System.out.println("Test Admins Added");

        };
    }
}

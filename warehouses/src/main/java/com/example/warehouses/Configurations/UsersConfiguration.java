package com.example.warehouses.Configurations;

import com.example.warehouses.Interfaces.Administrator;
import com.example.warehouses.Interfaces.Client;
import com.example.warehouses.Model.*;
import com.example.warehouses.Repository.AdminRepository;
import com.example.warehouses.Repository.ClientRepository;
import com.example.warehouses.Repository.RatingsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@Configuration
public class UsersConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(AdminRepository repository, ClientRepository clientRepository, RatingsRepository ratingsRepository){
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
            List<AgentRatings> ratingsBasedOnOwnerID = ratingsRepository.findByOwnerId(1L);
            List<AgentRatings> ratingsBasedOnAgentID = ratingsRepository.findByAgentId(3L);
//
            for (AgentRatings item: ratingsBasedOnOwnerID
              ) {
                System.out.println("Owner ratings with id's of 1L value " + item);
            }

            for (AgentRatings item: ratingsBasedOnAgentID
            ) {
                System.out.println("Agent ratings with id's of 3L value " + item);
            }



            System.out.println("Test Admins Added");

        };
    }
}

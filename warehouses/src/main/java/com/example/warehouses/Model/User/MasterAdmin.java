package com.example.warehouses.Model.User;

import com.example.warehouses.Interfaces.Administrator;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class MasterAdmin extends Administrator {

    public void init(String email, String password, String firstName, String lastName) {
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);

    }

    public Client createUser(String email,
                            String password,
                            String firstName,
                            String lastName,
                            String clientType) {
        Client client = null;
        if(clientType.toLowerCase().equals("owner")){
            client =  new Owner();
            ((Owner)client).init(email,password,firstName,lastName);
        }else{
            client = new Agent();
            ((Agent)client).init(email,password,firstName,lastName);
        }
        return client;
    }

    public Agent createAgent(String email,
                             String password,
                             String firstName,
                             String lastName) {
        Agent agent = new Agent();
        agent.init(email,password,firstName,lastName);
        return agent;
    }
}
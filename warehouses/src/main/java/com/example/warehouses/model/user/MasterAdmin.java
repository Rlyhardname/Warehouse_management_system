package com.example.warehouses.model.user;

import com.example.warehouses.interfaces.Administrator;
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

    public User createUser(String email,
                           String password,
                           String firstName,
                           String lastName,
                           String clientType) {
        User user = null;
        if(clientType.toLowerCase().equals("owner")){
            user =  new Owner();
            ((Owner) user).init(email,password,firstName,lastName);
        }else{
            user = new Agent();
            ((Agent) user).init(email,password,firstName,lastName);
        }
        return user;
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
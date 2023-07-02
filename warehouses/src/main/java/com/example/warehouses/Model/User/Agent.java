package com.example.warehouses.Model.User;

import com.example.warehouses.Exception.Warehouse.WarehouseNotExistingException;
import com.example.warehouses.Model.AgentRatings;
import com.example.warehouses.Model.warehouse.RentalForm;
import com.example.warehouses.Model.warehouse.Warehouse;
import com.example.warehouses.Model.warehouse.WarehouseAssignedToAgent;
import com.example.warehouses.Repository.WarehouseRepository;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Agent extends Client {


    public void rentWarehouse(WarehouseRepository repository, RentalForm receipt, Long warehouseId) {

        try {
            Warehouse warehouse = repository.findById(warehouseId).orElseThrow(() -> new IllegalStateException("Warehouse " + warehouseId + "doesn't exist"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RentalForm createContract(Agent agent, Client client, Warehouse warehouse, LocalDate startDate, LocalDate endDate, double contractFiatWorth, double agentFee) {

        RentalForm rentalForm = new RentalForm(agent,client,warehouse,startDate,endDate,contractFiatWorth,agentFee);
        return rentalForm;
    }

    public void init(String email, String password, String firstName, String lastName) {
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setAccountType("agent");
    }


    public boolean isRented(Warehouse warehouse) {

        if (warehouse != null) {
            throw new WarehouseNotExistingException();
        } else {
            if (warehouse.isRented() == true) {
                return true;
            }
        }
        return false;
    }

    public double calcAverageRating(List<AgentRatings> ratingsList) {
        double totalRatings = 0;
        double numberOfVotes = 0;

        if(ratingsList==null){
            return 0;
        }

        for (AgentRatings rating : ratingsList
        ) {
            totalRatings = rating.getStars();
            numberOfVotes++;
        }

        return totalRatings / numberOfVotes;
    }
}

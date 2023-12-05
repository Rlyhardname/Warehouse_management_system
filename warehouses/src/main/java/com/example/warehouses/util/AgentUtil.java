package com.example.warehouses.util;

import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.DTO.RentFormDTO;
import com.example.warehouses.Exception.Client.AgentHasNoContractsException;
import com.example.warehouses.Exception.Client.BadPathVariableException;
import com.example.warehouses.Exception.Client.UserNotExististingException;
import com.example.warehouses.Model.AgentRatings;
import com.example.warehouses.Model.User.Agent;
import com.example.warehouses.Model.User.User;
import com.example.warehouses.Model.warehouse.RentalForm;
import com.example.warehouses.Model.warehouse.Warehouse;
import com.example.warehouses.Repository.UsersRepository;
import com.example.warehouses.Repository.RatingsRepository;
import com.example.warehouses.Repository.RentalFormRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AgentUtil {

    public static boolean isAgentRated(UsersRepository usersRepository, RatingsRepository ratingsRepository, AgentAndRentFormDTO agentDTO, Long agentId) {
        User agent = usersRepository.findById(agentId).orElseThrow(() -> new UserNotExististingException());
        if (agent.getDType().equals("owner")) {
            throw new BadPathVariableException();
        }

        agentDTO.setEmail(agent.getEmail());
        agentDTO.setFirstName(agent.getFirstName());
        agentDTO.setLastName(agent.getLastName());
        double ratingsTotal = 0;
        int numberOfVotes = 0;
        Optional<List<AgentRatings>> ratingsOptList = ratingsRepository.findAllByAgentId(agentId);
        if (ratingsOptList.isPresent() == true) {
            numberOfVotes = ratingsOptList.get().size();
            for (AgentRatings rating : ratingsOptList.get()) {
                ratingsTotal += rating.getStars();
            }

        } else {
            return false;
        }

        agentDTO.setStarsTotal(ratingsTotal);
        agentDTO.setNumberOfVotes(numberOfVotes);
        return true;
    }

    public static void gatherFormData(RentalFormRepository rentalFormRepository, AgentAndRentFormDTO agentDTO, Long agentId, LocalDate startDate, LocalDate endDate) {
        List<RentalForm> rentalForms = rentalFormRepository.findRentFormsByAgentIdAndStartDateEndDate(agentId, startDate, endDate).orElseThrow(() -> new AgentHasNoContractsException());
        for (RentalForm form : rentalForms) {
            agentDTO.getRentalForms().add(new RentFormDTO(form.getId(), form.getAgent().getId(), form.getUser().getId(), form.getWarehouse().getId(), form.getStartDate(), form.getEndDate(), form.getContractFiatWorth()));
            System.out.println(form);
        }

    }

    public static RentalForm createContract(Agent agent, User user, Warehouse warehouse, LocalDate startDate, LocalDate endDate, double contractFiatWorth, double agentFee) {
        RentalForm rentalForm = new RentalForm(agent, user, warehouse, startDate, endDate, contractFiatWorth, agentFee);
        return rentalForm;
    }

    public static double calcAverageRating(List<AgentRatings> ratingsList) {
        double totalRatings = 0;
        if (ratingsList == null) {
            return 0;
        }

        for (AgentRatings rating : ratingsList
        ) {
            totalRatings = rating.getStars();
        }

        return totalRatings / ratingsList.size();
    }

}

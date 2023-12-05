package com.example.warehouses.util;

import com.example.warehouses.DTO.AgentAndRentFormDTO;
import com.example.warehouses.DTO.RentFormDTO;
import com.example.warehouses.exception.Client.AgentHasNoContractsException;
import com.example.warehouses.exception.Client.BadPathVariableException;
import com.example.warehouses.exception.Client.UserNotExististingException;
import com.example.warehouses.model.AgentRatings;
import com.example.warehouses.model.user.Agent;
import com.example.warehouses.model.user.User;
import com.example.warehouses.model.warehouse.RentalForm;
import com.example.warehouses.model.warehouse.Warehouse;
import com.example.warehouses.repository.UsersRepository;
import com.example.warehouses.repository.RatingsRepository;
import com.example.warehouses.repository.RentalFormRepository;

import java.time.LocalDate;
import java.util.List;

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
        List<AgentRatings> ratingsOptList = ratingsRepository.findAllByIdAgentID(agentId);
        if (!ratingsOptList.isEmpty()) {
            numberOfVotes = ratingsOptList.size();
            for (AgentRatings rating : ratingsOptList) {
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

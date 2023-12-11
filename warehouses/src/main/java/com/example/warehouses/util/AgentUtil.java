package com.example.warehouses.util;

import com.example.warehouses.DTO.RentFormDTO;
import com.example.warehouses.model.AgentRatings;
import com.example.warehouses.model.user.UserImpl;
import com.example.warehouses.model.warehouse.RentalForm;
import com.example.warehouses.model.warehouse.Warehouse;
import com.example.warehouses.repository.RatingsRepository;
import com.example.warehouses.repository.RentalFormRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgentUtil {

    public static boolean isAgentRated(Long agentId, RatingsRepository ratingsRepository) {
        return ratingsRepository.existsByIdAgentID(agentId);
    }

    public static List<AgentRatings> fetchRatingsList(Long agentId, RatingsRepository ratingsRepository) {
        return ratingsRepository.findAllByIdAgentID(agentId);
    }

    public static double averageRating(List<AgentRatings> ratings) {
        double ratingsTotal = 0;
        for (AgentRatings rating : ratings) {
            ratingsTotal += rating.getStars();
        }

        return ratingsTotal;
    }

    public static List<RentalForm> fetchRentalFormsList(RentalFormRepository rentalFormRepository, Long agentId, LocalDate startDate, LocalDate endDate) {
        List<RentalForm> rentalForms = rentalFormRepository.findRentFormsByAgentIdAndStartDateEndDate(agentId, startDate, endDate);
        System.out.println(rentalForms.size());
        // TODO think about if it's good to throw this exception or not..
        //        if(rentalForms.isEmpty()){
//            throw new AgentHasNoContractsException();
//        }
        return rentalForms;
    }

    public static RentalForm createContract(UserImpl agent, UserImpl userImpl, Warehouse warehouse, LocalDate startDate, LocalDate endDate, double contractFiatWorth, double agentFee) {
        RentalForm rentalForm = new RentalForm(agent, userImpl, warehouse, startDate, endDate, contractFiatWorth, agentFee);
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

    public static double totalStars(List<AgentRatings> ratings) {
        double stars = 0;
        for (AgentRatings curRating : ratings) {
            stars += curRating.getStars();
        }
        return stars;
    }

    public static List<RentFormDTO> createRentalFormDTO(List<RentalForm> allAgentRentalForms) {
        List<RentFormDTO> list = new ArrayList<>();
        for (var item : allAgentRentalForms) {
            list.add(new RentFormDTO(item.getId(),
                    item.getAgent().getId(),
                    item.getCustomer().getId(),
                    item.getWarehouse().getId(),
                    item.getStartDate(),
                    item.getEndDate(),
                    item.getContractFiatWorth()));
        }

        return list;
    }
}

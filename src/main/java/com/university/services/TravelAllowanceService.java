package com.university.services;

import com.university.entities.City;
import com.university.entities.TravelAllowance;
import com.university.entities.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface TravelAllowanceService {
    void saveTravelAllowance(TravelAllowance travelAllowance);

    void removeTravelAllowance(int id);

    List<TravelAllowance> getAllTravelAllowances();

    TravelAllowance getTravelAllowanceById(int id);

    Integer getCountOfTravelByCity(City city);

    List<TravelAllowance> getAllTravelAllowances(int department, int position, String date);

    List<TravelAllowance> getTravelAllowancesByUserAndDateOfIssue(User user, Optional<LocalDate> date);

}

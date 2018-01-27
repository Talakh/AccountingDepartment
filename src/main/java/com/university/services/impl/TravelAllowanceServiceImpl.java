package com.university.services.impl;

import com.university.entities.City;
import com.university.entities.TravelAllowance;
import com.university.entities.User;
import com.university.repositories.TravelAllowanceRepository;
import com.university.services.TravelAllowanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TravelAllowanceServiceImpl implements TravelAllowanceService {

    private final TravelAllowanceRepository travelAllowanceRepository;

    @Autowired
    public TravelAllowanceServiceImpl(TravelAllowanceRepository travelAllowanceRepository) {
        this.travelAllowanceRepository = travelAllowanceRepository;
    }

    @Override
    public void saveTravelAllowance(TravelAllowance travelAllowance) {
        travelAllowanceRepository.saveAndFlush(travelAllowance);
    }

    @Override
    public void removeTravelAllowance(int id) {
        travelAllowanceRepository.delete(id);
    }

    @Override
    public List<TravelAllowance> getAllTravelAllowances() {
        return travelAllowanceRepository.findAll();
    }

    @Override
    public TravelAllowance getTravelAllowanceById(int id) {
        return travelAllowanceRepository.findOne(id);
    }

    @Override
    public List<TravelAllowance> getTravelAllowancesByUserAndDateOfIssue(User user, Optional<LocalDate> date) {
        if (date.isPresent()) {
            return travelAllowanceRepository.findTravelAllowancesByUserAndDateOfIssueBetween(
                    user,
                    LocalDate.of(date.get().getYear(), date.get().getMonth(), 1),
                    LocalDate.of(date.get().getYear(), date.get().getMonth(), date.get().lengthOfMonth()));
        } else {
            return travelAllowanceRepository.findTravelAllowancesByUser(user);
        }
    }

    @Override
    public Integer getCountOfTravelByCity(City city) {
        return travelAllowanceRepository.countByCity(city);
    }

    @Override
    public List<TravelAllowance> getAllTravelAllowances(int department, int position, String date) {
        return travelAllowanceRepository.findByFilter(department,
                position,
                Integer.parseInt(date.split("-")[0]),
                Integer.parseInt(date.split("-")[1]));
    }
}

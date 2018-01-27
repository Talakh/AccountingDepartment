package com.university.services;

import com.university.entities.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {
    void addCity(City city);

    void removeCity(int id);

    City getCity(int id);

    List<City> getAllCities();
}

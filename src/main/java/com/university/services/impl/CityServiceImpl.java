package com.university.services.impl;

import com.university.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import com.university.repositories.CityRepository;
import com.university.services.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void addCity(City city) {
        cityRepository.saveAndFlush(city);
    }

    @Override
    public void removeCity(int id) {
        cityRepository.delete(id);
    }

    @Override
    public City getCity(int id) {
        return cityRepository.findOne(id);
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}

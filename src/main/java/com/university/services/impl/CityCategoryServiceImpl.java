package com.university.services.impl;

import com.university.entities.CityCategory;
import com.university.repositories.CityCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.university.services.CityCategoryService;

@Service
public class CityCategoryServiceImpl implements CityCategoryService {

    private final CityCategoryRepository cityCategoryRepository;

    @Autowired
    public CityCategoryServiceImpl(CityCategoryRepository cityCategoryRepository) {
        this.cityCategoryRepository = cityCategoryRepository;
    }

    @Override
    public void addCityCategory(CityCategory cityCategory) {
        cityCategoryRepository.saveAndFlush(cityCategory);
    }

    @Override
    public void removeCityCategory(int id) {
        cityCategoryRepository.delete(id);
    }

    @Override
    public CityCategory getCityCategory(int id) {
        return cityCategoryRepository.findOne(id);
    }
}

package com.university.services;

import com.university.entities.CityCategory;
import org.springframework.stereotype.Service;

@Service
public interface CityCategoryService {
    void addCityCategory(CityCategory cityCategory);

    void removeCityCategory(int id);

    CityCategory getCityCategory(int id);
}

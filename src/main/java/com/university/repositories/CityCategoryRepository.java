package com.university.repositories;

import com.university.entities.CityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityCategoryRepository extends JpaRepository<CityCategory, Integer> {
}

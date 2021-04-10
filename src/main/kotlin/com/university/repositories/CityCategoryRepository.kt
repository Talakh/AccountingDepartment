package com.university.repositories

import com.university.entities.CityCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CityCategoryRepository : JpaRepository<CityCategory, Int>

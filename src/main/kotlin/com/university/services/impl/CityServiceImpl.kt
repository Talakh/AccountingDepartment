package com.university.services.impl

import com.university.entities.City
import com.university.repositories.CityRepository
import com.university.services.CityService
import org.springframework.stereotype.Service

@Service
class CityServiceImpl (private val cityRepository: CityRepository) : CityService {
    override fun getAll(): List<City> {
        return cityRepository.findAll()
    }
}

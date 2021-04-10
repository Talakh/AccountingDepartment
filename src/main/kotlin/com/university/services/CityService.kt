package com.university.services

import com.university.entities.City

interface CityService {
    fun getAll(): List<City>
}

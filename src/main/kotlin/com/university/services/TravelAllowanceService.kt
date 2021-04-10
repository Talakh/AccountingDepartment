package com.university.services

import com.university.entities.City
import com.university.entities.TravelAllowance
import com.university.entities.User
import java.time.LocalDate

interface TravelAllowanceService {
    fun save(travelAllowance: TravelAllowance)

    fun removeById(id: Int)

    fun getCountByCity(city: City): Int

    fun getAll(department: Int, position: Int, date: String): List<TravelAllowance>

    fun findByUserAndDateOfIssue(user: User, date: LocalDate?): List<TravelAllowance>
}

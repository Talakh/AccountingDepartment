package com.university.services.impl

import com.university.entities.City
import com.university.entities.TravelAllowance
import com.university.entities.User
import com.university.repositories.TravelAllowanceRepository
import com.university.services.TravelAllowanceService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TravelAllowanceServiceImpl (private val travelAllowanceRepository: TravelAllowanceRepository) : TravelAllowanceService {
    override fun save(travelAllowance: TravelAllowance) {
        travelAllowanceRepository.saveAndFlush(travelAllowance)
    }

    override fun removeById(id: Int) {
        travelAllowanceRepository.deleteById(id)
    }

    override fun findByUserAndDateOfIssue(user: User, date: LocalDate?): List<TravelAllowance> {
        return if (date != null) {
            travelAllowanceRepository.findTravelAllowancesByUserAndDateOfIssueBetween(
                    user,
                    LocalDate.of(date.year, date.month, 1),
                    LocalDate.of(date.year, date.month, date.lengthOfMonth()))
        } else {
            travelAllowanceRepository.findTravelAllowancesByUser(user)
        }
    }

    override fun getCountByCity(city: City): Int {
        return travelAllowanceRepository.countByCity(city)
    }

    override fun getAll(department: Int, position: Int, date: String): List<TravelAllowance> {
        return travelAllowanceRepository.findByFilter(department,
                position, date.split("-".toRegex()).toTypedArray()[0].toInt(), date.split("-".toRegex()).toTypedArray()[1].toInt())
    }
}

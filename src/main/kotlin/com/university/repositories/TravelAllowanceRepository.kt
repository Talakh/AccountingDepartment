package com.university.repositories

import com.university.entities.City
import com.university.entities.TravelAllowance
import com.university.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface TravelAllowanceRepository : JpaRepository<TravelAllowance, Int> {
    fun findTravelAllowancesByUser(user: User): List<TravelAllowance>
    fun countByCity(city: City): Int

    @Query("select t from TravelAllowance t where " +
            "(-1=:department OR t.user.department.id=:department) and " +
            "(-1=:position OR t.user.position.id=:position) and " +
            "(0=:year OR YEAR(t.dateOfIssue)=:year) and " +
            "(0=:month OR MONTH(t.dateOfIssue)=:month)")
    fun findByFilter(@Param("department") department: Int,
                     @Param("position") position: Int,
                     @Param("year") year: Int,
                     @Param("month") month: Int): List<TravelAllowance>

    fun findTravelAllowancesByUserAndDateOfIssueBetween(user: User, start: LocalDate, end: LocalDate): List<TravelAllowance>
}

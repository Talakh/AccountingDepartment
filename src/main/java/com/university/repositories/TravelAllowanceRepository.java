package com.university.repositories;

import com.university.entities.City;
import com.university.entities.TravelAllowance;
import com.university.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TravelAllowanceRepository extends JpaRepository<TravelAllowance, Integer> {

    List<TravelAllowance> findTravelAllowancesByUser(User user);

    Integer countByCity(City city);

    @Query("select t from TravelAllowance t where " +
            "(-1=:department OR t.user.department.id=:department) and " +
            "(-1=:position OR t.user.position.id=:position) and " +
            "(0=:year OR YEAR(t.dateOfIssue)=:year) and " +
            "(0=:month OR MONTH(t.dateOfIssue)=:month)")
    List<TravelAllowance> findByFilter(@Param("department") int department,
                                       @Param("position") int position,
                                       @Param("year") int year,
                                       @Param("month") int month);

    List<TravelAllowance> findTravelAllowancesByUserAndDateOfIssueBetween(User user, LocalDate start, LocalDate end);
}

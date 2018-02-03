package com.university.repositories;

import com.university.entities.CashOrder;
import com.university.entities.TravelAllowance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.Repository;

import javax.transaction.Transactional;

public interface CashOrderRepository extends JpaRepository<CashOrder, Integer>, Repository<CashOrder, Integer> {

    CashOrder findCashOrderByTravelAllowance_Id(Integer travelAllowanceId);

    boolean existsByTravelAllowance(TravelAllowance travelAllowance);

    @Transactional
    @Modifying
    void removeCashOrderByTravelAllowance_Id(Integer travelAllowanceId);
}

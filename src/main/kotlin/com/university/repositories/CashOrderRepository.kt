package com.university.repositories

import com.university.entities.CashOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface CashOrderRepository : JpaRepository<CashOrder, Int> {
    @Transactional
    @Modifying
    fun removeCashOrderByTravelAllowanceId(travelAllowanceId: Int)
}

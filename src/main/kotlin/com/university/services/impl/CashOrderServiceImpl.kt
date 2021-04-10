package com.university.services.impl

import com.university.entities.CashOrder
import com.university.repositories.CashOrderRepository
import com.university.services.CashOrderService
import org.springframework.stereotype.Service

@Service
class CashOrderServiceImpl (private val cashOrderRepository: CashOrderRepository) : CashOrderService {
    override fun save(cashOrder: CashOrder): CashOrder {
        return cashOrderRepository.saveAndFlush(cashOrder)
    }

    override fun removeByTravelAllowanceId(travelAllowanceId: Int) {
        cashOrderRepository.removeCashOrderByTravelAllowanceId(travelAllowanceId)
    }
}

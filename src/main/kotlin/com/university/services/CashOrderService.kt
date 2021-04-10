package com.university.services

import com.university.entities.CashOrder

interface CashOrderService {
    fun save(cashOrder: CashOrder): CashOrder
    fun removeByTravelAllowanceId(travelAllowanceId: Int)
}

package com.university.services;

import com.university.entities.CashOrder;
import com.university.entities.TravelAllowance;
import org.springframework.stereotype.Service;

@Service
public interface CashOrderService {
    void addCashOrder(CashOrder cashOrder);

    void removeCashOrder(int id);

    CashOrder getCashOrder(int id);

    CashOrder findCashOrderByTravelAllowance_Id(Integer travelAllowanceId);

    void removeCashOrderByTravelAllowance_Id(Integer travelAllowanceId);

    boolean isCashOrderExistByTravelAllowance(TravelAllowance travelAllowance);
}

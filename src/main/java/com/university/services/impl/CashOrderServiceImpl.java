package com.university.services.impl;

import com.university.entities.CashOrder;
import com.university.repositories.CashOrderRepository;
import com.university.services.CashOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashOrderServiceImpl implements CashOrderService {

    private final CashOrderRepository cashOrderRepository;

    @Autowired
    public CashOrderServiceImpl(CashOrderRepository cashOrderRepository) {
        this.cashOrderRepository = cashOrderRepository;
    }

    @Override
    public void addCashOrder(CashOrder cashOrder) {
        cashOrderRepository.saveAndFlush(cashOrder);
    }

    @Override
    public void removeCashOrder(int id) {
        cashOrderRepository.delete(id);
    }

    @Override
    public CashOrder getCashOrder(int id) {
        return cashOrderRepository.findOne(id);
    }

    @Override
    public CashOrder findCashOrderByTravelAllowance_Id(Integer travelAllowanceId) {
        return cashOrderRepository.findCashOrderByTravelAllowance_Id(travelAllowanceId);
    }

    @Override
    public void removeCashOrderByTravelAllowance_Id(Integer travelAllowanceId) {
        cashOrderRepository.removeCashOrderByTravelAllowance_Id(travelAllowanceId);
    }

}

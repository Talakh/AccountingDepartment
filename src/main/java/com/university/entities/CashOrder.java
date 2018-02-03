package com.university.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "cash_order")
public class CashOrder {

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "Sum", nullable = false)
    private double sum;

    @Column(name = "DateReceiptOfMoney", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateReceiptOfMoney;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "TravelAllowance_Id", nullable = false)
    private TravelAllowance travelAllowance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public LocalDate getDateReceiptOfMoney() {
        return dateReceiptOfMoney;
    }

    public void setDateReceiptOfMoney(LocalDate dateReceiptOfMoney) {
        this.dateReceiptOfMoney = dateReceiptOfMoney;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TravelAllowance getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(TravelAllowance travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashOrder cashOrder = (CashOrder) o;
        return id == cashOrder.id &&
                Double.compare(cashOrder.sum, sum) == 0 &&
                Objects.equals(dateReceiptOfMoney, cashOrder.dateReceiptOfMoney) &&
                Objects.equals(user, cashOrder.user) &&
                Objects.equals(travelAllowance, cashOrder.travelAllowance);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, sum, dateReceiptOfMoney, user, travelAllowance);
    }

    @Override
    public String toString() {
        return "CashOrder{" +
                "id=" + id +
                ", sum=" + sum +
                ", dateReceiptOfMoney=" + dateReceiptOfMoney +
                ", user=" + user +
                ", travelAllowance=" + travelAllowance +
                '}';
    }
}

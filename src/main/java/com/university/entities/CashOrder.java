package com.university.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;

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

        if (id != cashOrder.id) return false;
        if (Double.compare(cashOrder.sum, sum) != 0) return false;
        if (!dateReceiptOfMoney.equals(cashOrder.dateReceiptOfMoney)) return false;
        if (!user.equals(cashOrder.user)) return false;
        return travelAllowance.equals(cashOrder.travelAllowance);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(sum);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + dateReceiptOfMoney.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + travelAllowance.hashCode();
        return result;
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

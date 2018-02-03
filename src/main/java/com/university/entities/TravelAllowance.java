package com.university.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "travel_allowance")
public class TravelAllowance {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "DateOfIssue", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfIssue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "City_Id", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "travelAllowance", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private CashOrder cashOrder;

    @OneToOne(mappedBy = "travelAllowance", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private PrepaymentReport prepaymentReport;

    @Column(name = "BusinessTripStartDate", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate businessTripStartDate;

    @Column(name = "BusinessTripEndDate", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate businessTripEndDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CashOrder getCashOrder() {
        return cashOrder;
    }

    public void setCashOrder(CashOrder cashOrder) {
        this.cashOrder = cashOrder;
    }

    public LocalDate getBusinessTripEndDate() {
        return businessTripEndDate;
    }

    public void setBusinessTripEndDate(LocalDate businessTripEndDate) {
        this.businessTripEndDate = businessTripEndDate;
    }

    public LocalDate getBusinessTripStartDate() {

        return businessTripStartDate;
    }

    public void setBusinessTripStartDate(LocalDate businessTripStartDate) {
        this.businessTripStartDate = businessTripStartDate;
    }

    public PrepaymentReport getPrepaymentReport() {
        return prepaymentReport;
    }

    public void setPrepaymentReport(PrepaymentReport prepaymentReport) {
        this.prepaymentReport = prepaymentReport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelAllowance that = (TravelAllowance) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dateOfIssue, that.dateOfIssue) &&
                Objects.equals(city, that.city) &&
                Objects.equals(user, that.user) &&
                Objects.equals(cashOrder, that.cashOrder) &&
                Objects.equals(prepaymentReport, that.prepaymentReport) &&
                Objects.equals(businessTripStartDate, that.businessTripStartDate) &&
                Objects.equals(businessTripEndDate, that.businessTripEndDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dateOfIssue, city, user, cashOrder, prepaymentReport, businessTripStartDate, businessTripEndDate);
    }

    @Override
    public String toString() {
        return "TravelAllowance{" +
                "id=" + id +
                ", dateOfIssue=" + dateOfIssue +
                ", city=" + city +
                ", user=" + user +
                ", businessTripStartDate=" + businessTripStartDate +
                ", businessTripEndDate=" + businessTripEndDate +
                '}';
    }
}

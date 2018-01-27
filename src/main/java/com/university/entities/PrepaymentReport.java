package com.university.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;

@Entity
@Table(name = "prepayment_report")
public class PrepaymentReport {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TravelAllowance_Id", nullable = false)
    private TravelAllowance travelAllowance;

    @Column(name = "Fare", nullable = false)
    private double fare;

    @Column(name = "SeatReservation", nullable = false)
    private double seatReservation;

    @Column(name = "HotelAccommodation", nullable = false)
    private double hotelAccommodation;

    @Column(name = "TelephoneConversations", nullable = false)
    private double telephoneConversations;

    @Column(name = "SumPerDiems", nullable = false)
    private double sumPerDiems;

    @Column(name = "PreparationDate", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate preparationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TravelAllowance getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(TravelAllowance travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public double getSeatReservation() {
        return seatReservation;
    }

    public void setSeatReservation(double seatReservation) {
        this.seatReservation = seatReservation;
    }

    public double getHotelAccommodation() {
        return hotelAccommodation;
    }

    public void setHotelAccommodation(double hotelAccommodation) {
        this.hotelAccommodation = hotelAccommodation;
    }

    public double getTelephoneConversations() {
        return telephoneConversations;
    }

    public void setTelephoneConversations(double telephoneConversations) {
        this.telephoneConversations = telephoneConversations;
    }

    public double getSumPerDiems() {
        return sumPerDiems;
    }

    public void setSumPerDiems(double sumPerDiems) {
        this.sumPerDiems = sumPerDiems;
    }

    public LocalDate getPreparationDate() {
        return preparationDate;
    }

    public void setPreparationDate(LocalDate preparationDate) {
        this.preparationDate = preparationDate;
    }

    public double getFullPrepaymentReportSum() {
        return getFare() +
                getHotelAccommodation() +
                getSeatReservation() +
                getSumPerDiems() +
                getTelephoneConversations();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrepaymentReport that = (PrepaymentReport) o;

        if (id != that.id) return false;
        if (Double.compare(that.fare, fare) != 0) return false;
        if (Double.compare(that.seatReservation, seatReservation) != 0) return false;
        if (Double.compare(that.hotelAccommodation, hotelAccommodation) != 0) return false;
        if (Double.compare(that.telephoneConversations, telephoneConversations) != 0) return false;
        if (Double.compare(that.sumPerDiems, sumPerDiems) != 0) return false;
        if (!travelAllowance.equals(that.travelAllowance)) return false;
        return preparationDate.equals(that.preparationDate);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + travelAllowance.hashCode();
        temp = Double.doubleToLongBits(fare);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(seatReservation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(hotelAccommodation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(telephoneConversations);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(sumPerDiems);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + preparationDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PrepaymentReport{" +
                "id=" + id +
                ", travelAllowance=" + travelAllowance +
                ", fare=" + fare +
                ", seatReservation=" + seatReservation +
                ", hotelAccommodation=" + hotelAccommodation +
                ", telephoneConversations=" + telephoneConversations +
                ", sumPerDiems=" + sumPerDiems +
                ", preparationDate=" + preparationDate +
                '}';
    }
}

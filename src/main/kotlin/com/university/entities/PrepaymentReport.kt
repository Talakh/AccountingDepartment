package com.university.entities

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "prepayment_report")
class PrepaymentReport {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Int? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_allowance_id", nullable = false)
    var travelAllowance: TravelAllowance? = null

    @Column(name = "fare", nullable = false)
    var fare = 0.0

    @Column(name = "seat_reservation", nullable = false)
    var seatReservation = 0.0

    @Column(name = "hotel_accommodation", nullable = false)
    var hotelAccommodation = 0.0

    @Column(name = "telephone_conversations", nullable = false)
    var telephoneConversations = 0.0

    @Column(name = "sum_per_diems", nullable = false)
    var sumPerDiems = 0.0

    @Column(name = "preparation_date", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    var preparationDate: LocalDate? = null
    val fullPrepaymentReportSum: Double
        get() = fare +
                hotelAccommodation +
                seatReservation +
                sumPerDiems +
                telephoneConversations
}

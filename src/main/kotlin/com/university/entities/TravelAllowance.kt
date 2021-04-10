package com.university.entities

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "travel_allowance")
class TravelAllowance {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    @Column(name = "date_of_issue", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    var dateOfIssue: LocalDate? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_Id", nullable = false)
    var city: City? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    var user: User? = null

    @OneToOne(mappedBy = "travelAllowance", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    var cashOrder: CashOrder? = null

    @OneToOne(mappedBy = "travelAllowance", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    var prepaymentReport: PrepaymentReport? = null

    @Column(name = "business_trip_start_date", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    var businessTripStartDate: LocalDate? = null

    @Column(name = "business_trip_end_date", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    var businessTripEndDate: LocalDate? = null
}

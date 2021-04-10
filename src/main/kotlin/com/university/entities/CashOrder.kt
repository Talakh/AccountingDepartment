package com.university.entities

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "cash_order")
class CashOrder {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    @Column(name = "sum", nullable = false)
    var sum: Double = 0.0

    @Column(name = "date_receipt_of_money", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    var dateReceiptOfMoney: LocalDate? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    var user: User? = null

    @OneToOne
    @JoinColumn(name = "travel_allowance_id", nullable = false)
    var travelAllowance: TravelAllowance? = null
}

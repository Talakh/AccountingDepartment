package com.university.entities

import javax.persistence.*

@Entity
@Table(name = "city_category")
class CityCategory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    @Column(name = "name", nullable = false, length = 25)
    var name: String = ""

    @Column(name = "cost_per_day", nullable = false)
    var costPerDay: Double? = 0.0
}

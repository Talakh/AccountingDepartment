package com.university.entities

import javax.persistence.*

@Entity
@Table(name = "city")
class City {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    @Column(name = "name", nullable = false, length = 25)
    var name: String = ""

    @Column(name = "travel_cost", nullable = false)
    var travelCost: Double = 0.0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    var cityCategory: CityCategory? = null
}

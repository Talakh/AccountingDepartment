package com.university.entities

import javax.persistence.*

@Entity
@Table(name = "position")
class Position {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    @Column(name = "name", nullable = false, length = 30)
    var name: String = ""
}

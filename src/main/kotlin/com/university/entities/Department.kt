package com.university.entities

import javax.persistence.*

@Entity
@Table(name = "department")
class Department {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Int? = null

    @Column(name = "name", nullable = false, length = 30)
    var name: String = ""

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "department_position", joinColumns = [JoinColumn(name = "department_Id")], inverseJoinColumns = [JoinColumn(name = "position_Id")])
    var positions: List<Position> = mutableListOf()
}

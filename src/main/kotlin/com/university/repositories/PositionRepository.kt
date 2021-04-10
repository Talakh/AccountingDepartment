package com.university.repositories

import com.university.entities.Position
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PositionRepository : JpaRepository<Position, Int> {
    fun findAllByOrderByNameAsc(): List<Position>
}

package com.university.services

import com.university.entities.Position

interface PositionService {
    fun getAll(): List<Position>
}

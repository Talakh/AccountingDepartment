package com.university.services.impl

import com.university.entities.Position
import com.university.repositories.PositionRepository
import com.university.services.PositionService
import org.springframework.stereotype.Service

@Service
class PositionServiceImpl (private val positionRepository: PositionRepository) : PositionService {
    override fun getAll(): List<Position> {
        return positionRepository.findAllByOrderByNameAsc()
    }
}

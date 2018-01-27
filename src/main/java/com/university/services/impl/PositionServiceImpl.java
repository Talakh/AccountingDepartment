package com.university.services.impl;

import com.university.entities.Position;
import com.university.repositories.PositionRepository;
import com.university.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public void addPosition(Position position) {
        positionRepository.saveAndFlush(position);
    }

    @Override
    public void removePosition(int id) {
        positionRepository.delete(id);
    }

    @Override
    public Position getPosition(int id) {
        return positionRepository.findOne(id);
    }

    @Override
    public List<Position> getAllPositions() {
        return positionRepository.findAllByOrderByNameAsc();
    }
}

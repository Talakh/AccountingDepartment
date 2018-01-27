package com.university.services;

import com.university.entities.Position;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PositionService {
    void addPosition(Position position);

    void removePosition(int id);

    Position getPosition(int id);

    List<Position> getAllPositions();
}

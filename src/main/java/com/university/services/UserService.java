package com.university.services;

import com.university.entities.Department;
import com.university.entities.Position;
import com.university.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    void saveUser(User user, boolean edit);

    void removeUser(int id);

    User getUserByEmail(String email);

    User getUserById(int id);

    List<User> getUsersByFilter(Optional<Department> department, Optional<Position> position);

    List<User> getAllUsers();

    List<User> getUsersByDepartmentAndPosition(Department department, Position position);

    List<User> getUsersByDepartment(Department department);

    List<User> getUsersByPosition(Position position);
}

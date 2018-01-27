package com.university.services.impl;

import com.university.entities.Department;
import com.university.entities.Position;
import com.university.entities.User;
import com.university.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.university.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user, boolean edit) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void removeUser(int id) {
        userRepository.delete(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByFilter(Optional<Department> department, Optional<Position> position) {
        if (department.isPresent() && position.isPresent()) {
            return getUsersByDepartmentAndPosition(department.get(), position.get());
        } else if (department.isPresent()) {
            return getUsersByDepartment(department.get());
        } else if (position.isPresent()) {
            return getUsersByPosition(position.get());
        } else {
            return getAllUsers();
        }
    }

    @Override
    public List<User> getUsersByDepartmentAndPosition(Department department, Position position) {
        return userRepository.findUserByDepartmentAndPosition(department, position);
    }

    @Override
    public List<User> getUsersByDepartment(Department department) {
        return userRepository.findUserByDepartment(department);
    }

    @Override
    public List<User> getUsersByPosition(Position position) {
        return userRepository.findUserByPosition(position);
    }
}

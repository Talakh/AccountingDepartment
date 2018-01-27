package com.university.repositories;

import com.university.entities.Department;
import com.university.entities.Position;
import com.university.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findUserByDepartmentAndPosition(Department department, Position position);

    List<User> findUserByDepartment(Department department);

    List<User> findUserByPosition(Position position);

}

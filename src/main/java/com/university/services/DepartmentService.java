package com.university.services;

import com.university.entities.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {
    void addDepartment(Department department);

    void removeDepartment(int id);

    Department getDepartment(int id);

    List<Department> getAllDepartments();
}

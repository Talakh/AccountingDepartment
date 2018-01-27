package com.university.services.impl;

import com.university.entities.Department;
import com.university.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import com.university.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void addDepartment(Department department) {
        departmentRepository.saveAndFlush(department);
    }

    @Override
    public void removeDepartment(int id) {
        departmentRepository.delete(id);
    }

    @Override
    public Department getDepartment(int id) {
        return departmentRepository.findOne(id);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAllByOrderByNameAsc();
    }
}

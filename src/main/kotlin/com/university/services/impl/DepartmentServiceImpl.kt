package com.university.services.impl

import com.university.entities.Department
import com.university.repositories.DepartmentRepository
import com.university.services.DepartmentService
import org.springframework.stereotype.Service

@Service
class DepartmentServiceImpl (private val departmentRepository: DepartmentRepository) : DepartmentService {
    override fun getAll(): List<Department> {
        return departmentRepository.findAllByOrderByNameAsc()
    }
}

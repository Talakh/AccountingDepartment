package com.university.services

import com.university.entities.Department

interface DepartmentService {
    fun getAll(): List<Department>
}

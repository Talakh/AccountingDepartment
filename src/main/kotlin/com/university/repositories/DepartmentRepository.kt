package com.university.repositories

import com.university.entities.Department
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepository : JpaRepository<Department, Int> {
    fun findAllByOrderByNameAsc(): List<Department>
}

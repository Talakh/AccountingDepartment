package com.university.repositories

import com.university.entities.Department
import com.university.entities.Position
import com.university.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User
    fun findUserByDepartmentAndPosition(department: Department, position: Position): List<User>
    fun findUserByDepartment(department: Department): List<User>
    fun findUserByPosition(position: Position): List<User>
}

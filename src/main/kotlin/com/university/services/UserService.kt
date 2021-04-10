package com.university.services

import com.university.entities.Department
import com.university.entities.Position
import com.university.entities.User

interface UserService {
    fun save(user: User, edit: Boolean)

    fun remove(id: Int)

    fun findByEmail(email: String): User?

    fun findById(id: Int): User

    fun findByFilter(department: Department?, position: Position?): List<User>

    fun getAll(): List<User>

    fun findByDepartmentAndPosition(department: Department, position: Position): List<User>

    fun findByDepartment(department: Department): List<User>

    fun findByPosition(position: Position): List<User>
}

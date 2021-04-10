package com.university.services.impl

import com.university.entities.Department
import com.university.entities.Position
import com.university.entities.User
import com.university.repositories.UserRepository
import com.university.services.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl (private val userRepository: UserRepository) : UserService {
    override fun save(user: User, edit: Boolean) {
        userRepository.saveAndFlush(user)
    }

    override fun remove(id: Int) {
        userRepository.deleteById(id)
    }

    override fun findByEmail(email: String): User {
        return userRepository.findByEmail(email)
    }

    override fun findById(id: Int): User {
        return userRepository.findById(id).orElseThrow{RuntimeException()}
    }

    override fun getAll(): List<User> {
        return userRepository.findAll()
    }

    override fun findByFilter(department: Department?, position: Position?): List<User> {
        return if (department != null && position != null) {
            findByDepartmentAndPosition(department, position)
        } else if (department != null) {
            findByDepartment(department)
        } else if (position != null) {
            findByPosition(position)
        } else {
            getAll()
        }
    }

    override fun findByDepartmentAndPosition(department: Department, position: Position): List<User> {
        return userRepository.findUserByDepartmentAndPosition(department, position)
    }

    override fun findByDepartment(department: Department): List<User> {
        return userRepository.findUserByDepartment(department)
    }

    override fun findByPosition(position: Position): List<User> {
        return userRepository.findUserByPosition(position)
    }
}

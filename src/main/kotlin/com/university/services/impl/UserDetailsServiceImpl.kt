package com.university.services.impl

import com.university.entities.User
import com.university.services.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(private val userService: UserService) : UserDetailsService {


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    override fun loadUserByUsername(userName: String): UserDetails {
        val user = userService.findByEmail(userName) ?: throw UsernameNotFoundException("Username not found")
        return org.springframework.security.core.userdetails.User(user.email, user.password,
                true, true, true, true, getGrantedAuthorities(user))
    }

    // TODO: 08.04.2021 move authorities to roles
    private fun getGrantedAuthorities(user: User): List<GrantedAuthority> =
            mutableListOf(SimpleGrantedAuthority("ROLE_" + user.role))
}


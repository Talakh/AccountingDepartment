package com.university.services.impl

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest
class SimpleTest {

    @Autowired
    lateinit var userService: UserServiceImpl

    @Test
    fun test() {
        var user = userService.findByEmail("oleksii@gmail.com")
        user.password = BCryptPasswordEncoder().encode("123123")
        userService.save(user, true)
        print(user)
    }
}

package com.university

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AccountingDepartment

fun main(args: Array<String>) {
    runApplication<AccountingDepartment>(*args)
}

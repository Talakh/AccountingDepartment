package com.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.university")
@EnableJpaRepositories("com.university.repositories")
@EntityScan({"com.university.entities", "org.springframework.data.jpa.convert.threeten"})
public class AccountingDepartment {
    public static void main(String[] args) {
        SpringApplication.run(AccountingDepartment.class, args);
    }
}

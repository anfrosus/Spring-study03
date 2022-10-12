package com.example.spring_hw02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringHw02Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringHw02Application.class, args);
    }

}

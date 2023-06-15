package com.example.demo.user;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository studentRepository) {
        return args -> {
            studentRepository.deleteAll();
            User john = new User("John", LocalDate.of(2005, Month.JANUARY, 5), "test@gmail.com", "test");
            User alex = new User("Alex", LocalDate.of(2007, Month.JANUARY, 5), "test2@gmail.com", "test");

            studentRepository.saveAll(List.of(john, alex));
        };
    }
}

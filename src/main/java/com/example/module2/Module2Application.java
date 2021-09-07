package com.example.module2;

import com.example.module2.entities.User;
import com.example.module2.repositories.UserRepository;
import com.example.module2.services.RoleService;
import com.example.module2.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class Module2Application {

    public static void main(String[] args) {
        SpringApplication.run(Module2Application.class, args);
    }

    @Bean
    CommandLineRunner run(RoleService roleService, UserRepository userRepository, UserService userService) {
        return args -> {
            if (userRepository.count() == 0) {
                roleService.seedRolesIntoDB();
                userService.seedAdminToDB();
            }
        };
    }
}

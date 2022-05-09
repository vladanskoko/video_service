package com.springboot.videoservice;

import com.springboot.videoservice.model.Role;
import com.springboot.videoservice.model.User;
import com.springboot.videoservice.model.UserRole;
import com.springboot.videoservice.service.RoleService;
import com.springboot.videoservice.service.UserRoleService;
import com.springboot.videoservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class VideoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoServiceApplication.class, args);
	}

	CommandLineRunner run(RoleService roleService, UserService userService, UserRoleService userRoleService) {
		return args -> {
			roleService.saveRole(new Role(1L, "ROLE_ADMIN", new ArrayList<>()));
			roleService.saveRole(new Role(2L, "ROLE_USER", new ArrayList<>()));
			userService.saveUser(new User(1L, "vladan", "vladan@axiomq.com", "1234", new ArrayList<>()));
			userService.saveUser(new User(2L, "misa", "misa@axiomq.com", "1234", new ArrayList<>()));
			userRoleService.addRoleToUser(1L, 1L);
			userRoleService.addRoleToUser(2L, 2L);
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

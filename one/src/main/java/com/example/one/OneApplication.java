package com.example.one;

import com.example.one.model.Task;
import com.example.one.model.User;
import com.example.one.repository.UserRepository;
import com.example.one.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class OneApplication {


	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(OneApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository) {
		return args -> {
			userRepository.deleteAll();
			userRepository.save(new User(null, "admin", passwordEncoder.encode("adminPass"), Arrays.asList("ADMIN")));
			userRepository.save(new User(null, "user", passwordEncoder.encode("userPass"), Arrays.asList("USER")));
		};
	}

	@Bean
	CommandLineRunner run(TaskService taskService) {
		return args -> {
			log.info("Application started");


		};
	}
}

package com.example.one;

import com.example.one.model.Task;
import com.example.one.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class OneApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneApplication.class, args);
	}

	@Bean
	CommandLineRunner run(TaskService taskService) {
		return args -> {
			log.info("Application started");

			// Create a sample task for demonstration purposes
			Task sampleTask = new Task();
			sampleTask.setAssignee("John Doe");
			sampleTask.setSeverity(5);
			sampleTask.setDescription("Sample Task");
			sampleTask.setStoryPoint(3);

			taskService.addTask(sampleTask);
			taskService.findAllTasks();
		};
	}
}

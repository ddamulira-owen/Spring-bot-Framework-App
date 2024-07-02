package com.example.one.service;

import com.example.one.model.Task;
import com.example.one.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TaskService {

    @Autowired
    private TaskRepository repository;

    // CREATE
    public Task addTask(Task task) {
        task.setTaskId(UUID.randomUUID().toString().split("_")[0]);
        Task savedTask = repository.save(task);
        log.info("Saved task: {}", savedTask);
        return savedTask;
    }

    // READ ALL
    public List<Task> findAllTasks() {
        List<Task> tasks = repository.findAll();
        tasks.forEach(t -> log.info("Found task: {}", t));
        return tasks;
    }

    // READ BY ID
    public Task getTaskByTaskId(String taskId) {
        Task task = repository.findById(taskId).orElse(null);
        log.info("Retrieved task: {}", task);
        return task;
    }

    // READ BY SEVERITY
    public List<Task> getTaskBySeverity(int severity) {
        List<Task> tasks = repository.findBySeverity(severity);
        tasks.forEach(t -> log.info("Found task by severity {}: {}", severity, t));
        return tasks;
    }

    // READ BY ASSIGNEE
    public List<Task> getTaskByAssignee(String assignee) {
        List<Task> tasks = repository.getTaskByAssignee(assignee);
        tasks.forEach(t -> log.info("Found task by assignee {}: {}", assignee, t));
        return tasks;
    }

    // UPDATE
    public Task updateTask(Task taskRequest) {
        Task existingTask = repository.findById(taskRequest.getTaskId()).orElse(null);
        if (existingTask != null) {
            existingTask.setAssignee(taskRequest.getAssignee());
            existingTask.setSeverity(taskRequest.getSeverity());
            existingTask.setDescription(taskRequest.getDescription());
            existingTask.setStoryPoint(taskRequest.getStoryPoint());
            Task updatedTask = repository.save(existingTask);
            log.info("Updated task: {}", updatedTask);
            return updatedTask;
        }
        log.warn("Task not found with id: {}", taskRequest.getTaskId());
        return null;
    }

    // DELETE
    public String deleteTask(String taskId) {
        repository.deleteById(taskId);
        log.info("Deleted task with id: {}", taskId);
        return taskId + " task deleted from dashboard";
    }
}

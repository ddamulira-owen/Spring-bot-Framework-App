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
        log.trace("Attempting to add task: {}", task);
        boolean hasErrors = false;

        if (task.getAssignee() == null || task.getAssignee().isEmpty()) {
            log.error("Assignee field is missing");

            hasErrors = true;

        }
        if (task.getSeverity() == 0) {
            log.error("Severity field is missing");
            hasErrors = true;
        }
        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            log.error("Description field is missing");
            hasErrors = true;
        }
        if (task.getStoryPoint() == 0) {
            log.error("Story Point field is missing");
            hasErrors = true;
        }

        if (hasErrors) {
            log.error("Task cannot be added due to missing fields: {}", task);
            return null;
        }

        task.setTaskId(UUID.randomUUID().toString().split("_")[0]);
        Task savedTask = repository.save(task);
        log.info("Saved task: {}", savedTask);
        return savedTask;
    }

    // READ ALL
    public List<Task> findAllTasks() {
        log.trace("Attempting to retrieve all tasks");
        List<Task> tasks = repository.findAll();
        tasks.forEach(t -> log.info("Found task: {}", t));
        return tasks;
    }

    // READ BY ID
    public Task getTaskByTaskId(String taskId) {
        log.trace("Attempting to retrieve task with id: {}", taskId);
        Task task = repository.findById(taskId).orElse(null);
        log.info("Retrieved task: {}", task);
        return task;
    }

    // READ BY SEVERITY
    public List<Task> getTaskBySeverity(int severity) {
        log.trace("Attempting to retrieve tasks with severity: {}", severity);
        List<Task> tasks = repository.findBySeverity(severity);
        tasks.forEach(t -> log.info("Found task by severity {}: {}", severity, t));
        return tasks;
    }

    // READ BY ASSIGNEE
    public List<Task> getTaskByAssignee(String assignee) {
        log.trace("Attempting to retrieve tasks assigned to: {}", assignee);
        List<Task> tasks = repository.getTaskByAssignee(assignee);
        tasks.forEach(t -> log.info("Found task by assignee {}: {}", assignee, t));
        return tasks;
    }

    // UPDATE
    public Task updateTask(Task taskRequest) {
        log.trace("Attempting to update task: {}", taskRequest);
        if (taskRequest.getTaskId() == null) {
            log.error("Task id is null, cannot update task");
            return null;
        }

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
        log.error("Task not found with id: {}", taskRequest.getTaskId());
        return null;
    }

    // DELETE
    public String deleteTask(String taskId) {
        log.trace("Attempting to delete task with id: {}", taskId);
        if (taskId == null) {
            log.error("Task id is null, cannot delete task");
            return "Task id is null, cannot delete task";
        }

        Task existingTask = repository.findById(taskId).orElse(null);
        if (existingTask != null) {
            repository.deleteById(taskId);
            log.info("Deleted task with id: {}", taskId);
            return taskId + " task deleted from dashboard";
        }
        log.error("Task not found with id: {}", taskId);
        return "Task not found with id: " + taskId;
    }

    // Unsupported Method Warning
    public void handleUnsupportedMethod() {
        log.warn("Request method not supported");
    }
}

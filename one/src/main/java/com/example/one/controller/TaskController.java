package com.example.one.controller;

import com.example.one.model.Task;
import com.example.one.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Task createTask(@RequestBody Task task) {
        return service.addTask(task);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<Task> getTasks() {
        return service.findAllTasks();
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Task getTask(@PathVariable String taskId) {
        return service.getTaskByTaskId(taskId);
    }

    @GetMapping("/severity/{severity}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<Task> findTaskBySeverity(@PathVariable int severity) {
        return service.getTaskBySeverity(severity);
    }

    @GetMapping("/assignee/{assignee}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<Task> getAssignee(@PathVariable String assignee) {
        return service.getTaskByAssignee(assignee);
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Task updateTask(@PathVariable String taskId, @RequestBody Task task) {
        return service.updateTask(task);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteTask(@PathVariable String taskId) {
        return service.deleteTask(taskId);
    }
}

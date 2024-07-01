package com.example.one.service;

import com.example.one.model.Task;
import com.example.one.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    //CRUD
    public Task addTask (Task task){
        task.setTaskId(UUID.randomUUID().toString().split("_")[0]);
        return repository.save(task);
    }

    //CREATE
    public List<Task> findAllTasks(){
        return repository.findAll();
    }

    //READ
    public Task getTaskByTaskId (String taskId){
        return repository.findById(taskId).get();
    }

    public List<Task> getTaskBySeverity( int severity){
        return repository.findBySeverity(severity);
    }


    public List<Task> getTaskByAssignee(String assignee){
    return repository.getTaskByAssignee(assignee);
    }

    //UPDATE
    public Task updateTask(Task taskRequest){
        Task existingTask=repository.findById(taskRequest.getTaskId()).get();
        existingTask.setAssignee(taskRequest.getAssignee());
        existingTask.setSeverity(taskRequest.getSeverity());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setStoryPoint(taskRequest.getStoryPoint());
        return repository.save(existingTask);

    }

    public String deleteTask (String taskId){
        repository.deleteById(taskId);
        return taskId+"taskd deleted from dashborad";
    }

}

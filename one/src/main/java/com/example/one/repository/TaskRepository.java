package com.example.one.repository;

import com.example.one.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends MongoRepository  <Task, String>{



    List<Task> findBySeverity(int severity);


    @Query("{ assignee: ?0 }")
    List<Task> getTaskByAssignee(String assignee);





}

package com.chidrome.taskmaster.taskmaster.controllers;

import com.chidrome.taskmaster.taskmaster.models.TaskInfo;
import com.chidrome.taskmaster.taskmaster.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskmasterController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public ResponseEntity<Iterable<TaskInfo>> findalltask(){

        return ResponseEntity.ok(taskRepository.findAll());
    }

    @PostMapping("/tasks")
    public String createTask(String title, String description, String status){
        // Status should always start out as available
        return "created";
    }


    @PutMapping("/tasks/{id}/state")
    public String updateStatus(@PathVariable String id){
        return "udpated";
    }
}

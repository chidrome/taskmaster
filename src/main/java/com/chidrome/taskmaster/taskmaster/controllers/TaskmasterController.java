package com.chidrome.taskmaster.taskmaster.controllers;

import com.chidrome.taskmaster.taskmaster.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class TaskmasterController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public String getTasks(){
        return "hello";
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

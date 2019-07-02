package com.chidrome.taskmaster.taskmaster.controllers;

import com.chidrome.taskmaster.taskmaster.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskmasterController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public String getTasks(){
        return "hello";
    }

}

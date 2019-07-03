package com.chidrome.taskmaster.taskmaster.controllers;

import com.chidrome.taskmaster.taskmaster.models.TaskInfo;
import com.chidrome.taskmaster.taskmaster.repositories.TaskRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskmasterController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public ResponseEntity<Iterable<TaskInfo>> findalltask(){

        return ResponseEntity.ok(taskRepository.findAll());
    }

    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@RequestParam String title, @RequestParam String description, @RequestParam String assignee){
        // Status should always start out as available
        TaskInfo newTask = new TaskInfo(title, description, assignee, "available"); //all new tasks should start with available as status
        taskRepository.save(newTask);
        return ResponseEntity.ok("Task(s) created");
    }


    @PutMapping("/tasks/{id}/state")
    public ResponseEntity<String> updateStatus(@PathVariable String id){
        TaskInfo taskToUpdate = taskRepository.findById(id).get();
        if(taskToUpdate.getStatus().toLowerCase().equals("available")){
            taskToUpdate.setStatus("assigned");
        } else if(taskToUpdate.getStatus().toLowerCase().equals("assigned")){
            taskToUpdate.setStatus("accepted");
        } else {
            taskToUpdate.setStatus("completed");
        }
        taskRepository.save(taskToUpdate);
        return ResponseEntity.ok("Task has been updated");
    }

    @GetMapping("/users/{name}/tasks")
    public ResponseEntity<List> getUserTasks(@PathVariable String name){
        Iterable<TaskInfo> tasks = taskRepository.findAll();
        List<String> assigneeList = Lists.newArrayList();

        for (TaskInfo t : tasks) {
            if (t.getAssignee() != null) if (t.getAssignee().equals(name)) assigneeList.add(t.getDescription());
        }
        return ResponseEntity.ok(assigneeList);
    }

    @PutMapping("/tasks/{id}/assign/{assignee}")
    public ResponseEntity<TaskInfo> updateAssignee(@PathVariable String id, @PathVariable String assignee){
        TaskInfo task = taskRepository.findById(id).get();
        task.setAssignee(assignee);
        task.setStatus("assigned");
        taskRepository.save(task);
        return ResponseEntity.ok(task);
    }

}

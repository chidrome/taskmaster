package com.chidrome.taskmaster.taskmaster.controllers;

import com.amazonaws.Response;
import com.chidrome.taskmaster.taskmaster.S3Client;
import com.chidrome.taskmaster.taskmaster.models.TaskInfo;
import com.chidrome.taskmaster.taskmaster.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
public class TaskmasterController {

    private final S3Client s3Client;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskmasterController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping("/tasks")
    public ResponseEntity<Iterable<TaskInfo>> findalltask(){

        return ResponseEntity.ok(taskRepository.findAll());
    }

    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@RequestParam String title, @RequestParam String description, @RequestParam String assignee){
        // Status should always start out as available
        TaskInfo newTask = new TaskInfo(title, description, assignee, "available", ""); //all new tasks should start with available as status
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
        List<String> assigneeList = new ArrayList<>();

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

    @PostMapping("/tasks/{id}/images")
    public ResponseEntity<TaskInfo> updateImage(@PathVariable String id, @RequestPart(value = "file") MultipartFile file){
        TaskInfo task = taskRepository.findById(id).get();
        String pic = this.s3Client.uploadFile(file);
        task.setImage(pic);
        taskRepository.save(task);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskInfo> getTaskInfo(@PathVariable String id){
        TaskInfo task = taskRepository.findById(id).get();

        return ResponseEntity.ok(task);
    }


}

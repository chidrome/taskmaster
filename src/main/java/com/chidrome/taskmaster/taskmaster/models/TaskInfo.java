package com.chidrome.taskmaster.taskmaster.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.UUID;

@DynamoDBTable(tableName = "taskmaster")
public class TaskInfo {

    private UUID id;
    String title;
    String description;
    String assignee;
    String status;


    public TaskInfo(){}

    public TaskInfo(String title, String description, String assignee, String status){
        this.title = title;
        this.description = description;
        this.assignee = assignee;
        this.status = status;
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public UUID getId() {
        return id;
    }

    @DynamoDBAttribute
    public String getTitle() {
        return title;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }

    @DynamoDBAttribute
    public String getStatus() {
        return status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}